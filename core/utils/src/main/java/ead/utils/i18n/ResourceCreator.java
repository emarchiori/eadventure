/**
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the
 *    <e-UCM> research group.
 *
 *    Copyright 2005-2010 <e-UCM> research group.
 *
 *    You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *    <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *
 * ****************************************************************************
 *
 *  This file is part of eAdventure, version 2.0
 *
 *      eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with eAdventure.  If not, see <http://www.gnu.org/licenses/>.
 */

package ead.utils.i18n;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Set;
import java.util.TreeSet;

//TODO This class should be called for any project that has
//     resources upon building
/**
 * This class allows the automatic creation of the R.java files that define
 * the class which is used to internationalize resources in the application.
 * The R classes contain generated maps of all available resources in the
 * classpath, and should be regenerated as part of the release process. This
 * method is much quicker than scanning jar-files at execution time.
 *
 * R.java files imitate a similar mechanism developed for Android applications;
 * you can read more about it at
 * http://developer.android.com/guide/topics/resources/accessing-resources.html
 */
public class ResourceCreator {

	private static String eol = System.getProperty("line.separator");

	/**
	 * Generate the R.java file with the R class for the given project and package
	 *
	 * @param args projecteURL: the location of the project for which the R file must be generated
	 * packageName: the name of the main package in the project
	 */
	public static void main(String[] args) throws IOException {

        String regenName = ResourceCreator.class.getCanonicalName();
        if (args.length < 3 || args.length > 4 || (args.length > 0 && args[0].equals("-h"))) {
            System.err.println("Syntax: java -cp <classpath> "
                + regenName
                + " <project-location> <package-name> <license-file> [<source-location>]\n"
                + "Where \n"
                + "   classpath - "
                    + "the classpath you are using now\n"
                + "   project-location - "
                    + "location of the project whose resources you want to index\n"
                + "   license-file - "
                    + "name of the file with the license you want to pre-pend\n"
                + "   package-name - "
                    + "name of the package you want to create the R.java file in\n"
                + "   source-location - "
                    + "location for output R.java file; if absent, stdout is used\n");
            System.exit(-1);
        }

		String projectURL = args[0];       // .../eadventure.editor-core
		String packageName = args[1];      // ead.editor
        String licenseFileName = args[2];  // etc/LICENSE.txt
        PrintStream out = (args.length == 3) ?
                System.out : new PrintStream(args[3]);

        String importName = ResourceCreator.class.getCanonicalName()
                .replace(ResourceCreator.class.getSimpleName(), "I18N");

        // Expect a project-location/src/main/resources folder
		File resources =
			new File(projectURL + File.separator
                + "src" + File.separator
				+ "main" + File.separator
                + "resources");

        // build a paramString to include in class comment
        StringBuilder parameterString = new StringBuilder();
        for (String s : args) {
            parameterString.append(" \"").append(s).append("\"");
        }

        printLicense(licenseFileName, out);

		String classContent = eol
            + "package " + packageName + ";" + eol
            + eol
            + "import " + importName + ";" + eol
            + "import java.util.Set;" + eol
            + "import java.util.TreeSet;" + eol
            + eol
            + "/**" + eol
            + " * Resource index for this package (statically compiled)." + eol
            + " *" + eol
            + " * This is an AUTOMATICALLY-GENERATED file - " + eol
            + " * Run class " + regenName + " with parameters: " + eol
            + " *   " + parameterString.toString() + eol
            + " * to re-create or update this class" + eol
            + " */" + eol
            + "public class R {" + eol
            + eol
            + createClass(resources, "Drawable")
            + "}" + eol;

		out.println(classContent);
	}

    /**
     * Return file contents as a string
     */
    private static void printLicense(String fileName, PrintStream out) {
        File f = new File(fileName);
        BufferedReader br = null;
        try {
            out.println("/**");
            br = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), "UTF-8"));
            while (br.ready()) {
                out.println(" * " + br.readLine());
            }
            out.println(" */");
        } catch (IOException e) {
            System.err.println("Error adding license from '" + f.getAbsolutePath() + "'");
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	/**
	 * Create a sub-class, which contains the actual resources (e.g. "Drawable").
	 * Notice that "qualifiers" (-something extensions in the leading directories) 
	 * are ignored, to allow for internationalization.
	 *
	 * @param location the location of the resources
	 * @param className the name of the new class
	 * @return A string with the full definition of the sub-class
	 */
	private static String createClass(File location, final String className) {
		StringBuilder classContent = new StringBuilder(
                "\tpublic static class " + className + " {" + eol);

		FileFilter ff = new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.getName().startsWith(className.toLowerCase());
			}
		};
		
		Set<String> files = new TreeSet<String>();	
		for (File resources : location.getAbsoluteFile().listFiles(ff)) {
			String localeString = resources.getName().contains("-") ? 
					resources.getName().replaceAll(".*[-]", "") + "//" : "";
			for (File file : resources.listFiles()) {
				if (file.getName().startsWith(".")) {
					// ignore . and ..
				} else if (file.isDirectory())
					recursive(files, file.getName()
							+ File.separator, file);
				else {
					files.add(localeString + file.getName());
				}
			}
		}
		
		Set<String> res = new TreeSet<String>();
		for (String resource : files) {			
			// removes locales
			resource = resource.replaceAll(".*[/][/]", "");

			if ( ! resource.matches("^[a-zA-Z0-9_/]+[.][a-zA-Z0-9_]+$")) {
				System.err.println("Sorry, '" + resource + "' has an invalid name. \n"
						+ "\tPlease avoid spaces and any non-alphanumeric characters, such as '-+' or ':'; '_' is ok, though");
			} else if (resource.matches(".*[_][_].*")) {
				System.err.println("Sorry, '" + resource + "' has an invalid name. \n"
						+ "\tPlease avoid two '__' in a row; we use it for '/'-substitution...");				
			} else {
				resource = resource.replaceAll("/", "__").replace(".", "_");
				if ( ! res.contains(resource)) {
					classContent.append("\t\tpublic static String ")
							.append(resource).append(";")
							.append(eol);					
					res.add(resource);
				}
			}
		}
	
        classContent.append(eol)
                .append("\t\tstatic {").append(eol)
                .append("\t\t\tSet<String> files = new TreeSet<String>();").append(eol)
                .append(eol);

		for (String file : files) {
			classContent.append("\t\t\tfiles.add(\"").append(file.replaceAll("[/][/]", "/")).append("\");")
                    .append(eol);
        }

        classContent.append(eol)
            .append("\t\t\tI18N.initializeResources(Drawable.class.getName(),"
                + " Drawable.class, files);").append(eol)
            .append("\t\t}").append(eol)
            .append("\t}").append(eol);

		return classContent.toString();
	}

	/**
	 * Recursive method to visit all the sub-folders in the resource structure.
	 *
	 * @param files
	 * @param currentPath
	 * @param currentDir
	 */
	private static void recursive(Set<String> files, String currentPath, File currentDir) {
		for (File file : currentDir.listFiles()) {
			if (file.isDirectory())
				recursive(files, currentPath 
						+ File.separator, file);
			else {
				files.add(currentPath + file.getName());
			}
		}
	}
}
