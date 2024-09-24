package gsrs.clinicaltrials.tests;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import gsrs.startertests.pomutilities.PomUtilities;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ClinicalTrialsPomsVersionTest {
    // Check GSRS versions in all pom.xml, and select extraJars, installExtraJars.sh commands
    // against the values in pom-version.properties. Helpful when making a version change
    // for the whole GSRS project.
    // Test effectively skipped unless CLI param -DdoPomCheck=true
    // Run from command line:
    // mvn test -Dtest=gsrs.clinicaltrials.tests.ClinicalTrialsPomsVersionTest -DdoPomCheck=true -pl gsrs-module-clinical-trials-spring-boot-starter-tests

    // Set to true when testing in IDE
    boolean turnOffPomParameterCheck = false;
    String clinicalTrialsModuleVersion;
    String starterModuleVersion;
    String substancesModuleVersion;
    String otherModuleVersion;
    String rootDir;
    String propertiesFile;
    String installExtraJarsScriptText;
    boolean doPomCheck = false;

    @BeforeEach
    public void setup() {
        doPomCheck=Boolean.parseBoolean(System.getProperty("doPomCheck"));
        if(!doPomCheck && !turnOffPomParameterCheck) { return; }
        String scriptFile = "installExtraJars.sh";
        propertiesFile = "pom-version.properties";
        rootDir = "..";

        try {
            Properties properties = PomUtilities.readPomVersionProperties(rootDir + "/" + propertiesFile);


            starterModuleVersion = properties.getProperty("ct.pomversiontest.starterModuleVersion");
            substancesModuleVersion = properties.getProperty("ct.pomversiontest.substancesModuleVersion");
            clinicalTrialsModuleVersion = properties.getProperty("ct.pomversiontest.clinicalTrialsModuleVersion");

            assertNotNull(starterModuleVersion);
            assertNotNull(substancesModuleVersion);
            System.out.println("starterModuleVersion: " + starterModuleVersion);
            System.out.println("substancesModuleVersion: " + substancesModuleVersion);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        try {
//            installExtraJarsScriptText = PomUtilities.readTextFile(rootDir + "/" + scriptFile, StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Test
    public void testPomCheck() {
        if(!doPomCheck && !turnOffPomParameterCheck) {
            System.out.println("Effectively skipping testPomCheck because -DdoPomCheck is not true.");
            return;
        }
        Model rootModel;
        try {
            rootModel = PomUtilities.readPomToModel(rootDir + "/pom.xml");
            Properties properties = rootModel.getProperties();
            System.out.println("> checking root");
            assertEquals( clinicalTrialsModuleVersion, rootModel.getVersion(), "version");
            assertEquals(starterModuleVersion, properties.getProperty("gsrs.version"), "gsrs.version:");
            assertEquals( substancesModuleVersion, properties.getProperty("gsrs.substance.version"));
            List<String> modules = rootModel.getModules();
            for (String module : modules) {
                System.out.println("> checking "+ module);
                Model moduleModel;
                try {
                    moduleModel = PomUtilities.readPomToModel(rootDir + "/" + module + "/pom.xml");
                    String checkVersion = moduleModel.getParent().getVersion();
                    assertEquals (clinicalTrialsModuleVersion, checkVersion, "parent/version");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void checkDependencyExtraJarExistsAndFindPathInScript(Dependency dependency) {
        String jarPath = "extraJars/" + PomUtilities.makeJarFilename(dependency);
        File file = new File(rootDir + "/" + jarPath);
        assertTrue(file.exists());
        assertTrue(installExtraJarsScriptText.contains(jarPath));
    }
}
