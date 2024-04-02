package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.SerializableTestClass;
import seedu.address.testutil.TestUtil;

/**
 * Tests JSON Read and Write
 */
public class JsonUtilTest {

    private static final Path SERIALIZATION_FILE = TestUtil.getFilePathInSandboxFolder("serialize.json");

    @Test
    public void serializeObjectToJsonFile_noExceptionThrown() throws IOException {
        SerializableTestClass serializableTestClass = new SerializableTestClass();
        serializableTestClass.setTestValues();

        JsonUtil.serializeObjectToJsonFile(SERIALIZATION_FILE, serializableTestClass);

        assertEquals(FileUtil.readFromFile(SERIALIZATION_FILE), SerializableTestClass.JSON_STRING_REPRESENTATION);
    }

    @Test
    public void deserializeObjectFromJsonFile_noExceptionThrown() throws IOException {
        FileUtil.writeToFile(SERIALIZATION_FILE, SerializableTestClass.JSON_STRING_REPRESENTATION);

        SerializableTestClass serializableTestClass = JsonUtil
                .deserializeObjectFromJsonFile(SERIALIZATION_FILE, SerializableTestClass.class);

        assertEquals(serializableTestClass.getName(), SerializableTestClass.getNameTestValue());
        assertEquals(serializableTestClass.getListOfLocalDateTimes(), SerializableTestClass.getListTestValues());
        assertEquals(serializableTestClass.getMapOfIntegerToString(), SerializableTestClass.getHashMapTestValues());
    }

    //TODO: @Test jsonUtil_readJsonStringToObjectInstance_correctObject()
    @Test
    public void jsonUtil_readJsonStringToObjectInstance_correctObject() throws IOException {
        String jsonString = SerializableTestClass.JSON_STRING_REPRESENTATION;
        SerializableTestClass actualClassInstance = JsonUtil.fromJsonString(jsonString, SerializableTestClass.class);

        assertEquals(SerializableTestClass.getNameTestValue(), actualClassInstance.getName());
        assertEquals(SerializableTestClass.getListTestValues(), actualClassInstance.getListOfLocalDateTimes());
        assertEquals(SerializableTestClass.getHashMapTestValues(), actualClassInstance.getMapOfIntegerToString());
    }

    //TODO: @Test jsonUtil_writeThenReadObjectToJson_correctObject()
    @Test
    public void jsonUtil_writeThenReadObjectToJson_correctObject() throws IOException {
        SerializableTestClass originalClassInstance = new SerializableTestClass();
        originalClassInstance.setTestValues();

        String jsonString = JsonUtil.toJsonString(originalClassInstance);

        SerializableTestClass deserializedClassInstance =
                JsonUtil.fromJsonString(jsonString, SerializableTestClass.class);

        assertEquals(originalClassInstance.getName(), deserializedClassInstance.getName());
        assertEquals(originalClassInstance.getListOfLocalDateTimes(),
                deserializedClassInstance.getListOfLocalDateTimes());
        assertEquals(originalClassInstance.getMapOfIntegerToString(),
                deserializedClassInstance.getMapOfIntegerToString());
    }
}
