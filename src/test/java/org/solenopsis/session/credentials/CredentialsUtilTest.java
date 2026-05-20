package org.solenopsis.session.credentials;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.solenopsis.session.Credentials;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test for CredentialsUtil.
 */
class CredentialsUtilTest {

    @Test
    void testFromValues() {
        Credentials creds = CredentialsUtil.fromValues(
            "https://test.salesforce.com",
            "user@test.com",
            "password123",
            "token456",
            "58.0"
        );

        assertNotNull(creds);
        assertEquals("https://test.salesforce.com", creds.url());
        assertEquals("user@test.com", creds.username());
        assertEquals("password123", creds.password());
        assertEquals("token456", creds.token());
        assertEquals("58.0", creds.version());
    }

    @Test
    void testFromProperties() {
        Properties props = new Properties();
        props.setProperty("url", "https://login.salesforce.com");
        props.setProperty("username", "prod@company.com");
        props.setProperty("password", "prodPass");
        props.setProperty("token", "prodToken");
        props.setProperty("version", "59.0");

        Credentials creds = CredentialsUtil.fromProperties(props);

        assertNotNull(creds);
        assertEquals("https://login.salesforce.com", creds.url());
        assertEquals("prod@company.com", creds.username());
        assertEquals("prodPass", creds.password());
        assertEquals("prodToken", creds.token());
        assertEquals("59.0", creds.version());
    }

    @Test
    void testFromPropertiesNullThrows() {
        assertThrows(NullPointerException.class, () -> {
            CredentialsUtil.fromProperties(null);
        });
    }

    @Test
    void testFromInputStream() throws IOException {
        String propsContent =
            "url=https://test.salesforce.com\n" +
            "username=test@example.com\n" +
            "password=testPass\n" +
            "token=testToken\n" +
            "version=58.0\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(propsContent.getBytes());
        Credentials creds = CredentialsUtil.fromInputStream(inputStream);

        assertNotNull(creds);
        assertEquals("https://test.salesforce.com", creds.url());
        assertEquals("test@example.com", creds.username());
        assertEquals("testPass", creds.password());
        assertEquals("testToken", creds.token());
        assertEquals("58.0", creds.version());
    }

    @Test
    void testFromInputStreamWithCloseFlag() throws IOException {
        String propsContent =
            "url=https://test.salesforce.com\n" +
            "username=test@example.com\n" +
            "password=testPass\n" +
            "token=testToken\n" +
            "version=58.0\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(propsContent.getBytes());
        Credentials creds = CredentialsUtil.fromInputStream(inputStream, true);

        assertNotNull(creds);
        assertEquals("https://test.salesforce.com", creds.url());
    }

    @Test
    void testFromReader() {
        String propsContent =
            "url=https://test.salesforce.com\n" +
            "username=test@example.com\n" +
            "password=testPass\n" +
            "token=testToken\n" +
            "version=58.0\n";

        StringReader reader = new StringReader(propsContent);
        Credentials creds = CredentialsUtil.fromReader(reader);

        assertNotNull(creds);
        assertEquals("https://test.salesforce.com", creds.url());
        assertEquals("test@example.com", creds.username());
    }

    @Test
    void testFromReaderWithCloseFlag() {
        String propsContent =
            "url=https://test.salesforce.com\n" +
            "username=test@example.com\n" +
            "password=testPass\n" +
            "token=testToken\n" +
            "version=58.0\n";

        StringReader reader = new StringReader(propsContent);
        Credentials creds = CredentialsUtil.fromReader(reader, true);

        assertNotNull(creds);
        assertEquals("https://test.salesforce.com", creds.url());
    }

    @Test
    void testFromString() {
        String propsContent =
            "url=https://login.salesforce.com\n" +
            "username=prod@company.com\n" +
            "password=prodPass\n" +
            "token=prodToken\n" +
            "version=59.0\n";

        Credentials creds = CredentialsUtil.fromString(propsContent);

        assertNotNull(creds);
        assertEquals("https://login.salesforce.com", creds.url());
        assertEquals("prod@company.com", creds.username());
        assertEquals("prodPass", creds.password());
        assertEquals("prodToken", creds.token());
        assertEquals("59.0", creds.version());
    }

    @Test
    void testFromFile(@TempDir Path tempDir) throws IOException {
        File tempFile = tempDir.resolve("credentials.properties").toFile();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("url=https://test.salesforce.com\n");
            writer.write("username=file@test.com\n");
            writer.write("password=filePass\n");
            writer.write("token=fileToken\n");
            writer.write("version=58.0\n");
        }

        Credentials creds = CredentialsUtil.fromFile(tempFile);

        assertNotNull(creds);
        assertEquals("https://test.salesforce.com", creds.url());
        assertEquals("file@test.com", creds.username());
        assertEquals("filePass", creds.password());
        assertEquals("fileToken", creds.token());
        assertEquals("58.0", creds.version());
    }

    @Test
    void testFromFileString(@TempDir Path tempDir) throws IOException {
        File tempFile = tempDir.resolve("credentials.properties").toFile();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("url=https://login.salesforce.com\n");
            writer.write("username=string@test.com\n");
            writer.write("password=stringPass\n");
            writer.write("token=stringToken\n");
            writer.write("version=59.0\n");
        }

        Credentials creds = CredentialsUtil.fromFile(tempFile.getAbsolutePath());

        assertNotNull(creds);
        assertEquals("https://login.salesforce.com", creds.url());
        assertEquals("string@test.com", creds.username());
        assertEquals("stringPass", creds.password());
        assertEquals("stringToken", creds.token());
        assertEquals("59.0", creds.version());
    }

    @Test
    void testGetLogger() {
        assertNotNull(CredentialsUtil.getLogger());
    }
}
