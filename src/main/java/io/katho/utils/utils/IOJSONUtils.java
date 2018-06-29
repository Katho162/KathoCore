package io.katho.utils.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class IOJSONUtils {

    /**
     * Save a JSONObject to a file.
     * @param path is the file path as String.
     * @param obj the JSONObject to be saved.
     * @throws IOException
     */
    public static void write(String path, JSONObject obj) throws IOException {
        FileUtils.writeStringToFile(new File(path), obj.toJSONString(), "UTF-8");
    }

    /**
     * Save a JSONObject to a file.
     * @param file is the file
     * @param obj the JSONObject to be saved.
     * @throws IOException
     */
    public static void write(File file, JSONObject obj) throws IOException {
        FileUtils.writeStringToFile(file, obj.toJSONString(), "UTF-8");
    }

    /**
     * Save JSONObject to a file
     * @param path is the file path as URI
     * @param obj the JSONObject to be saved.
     * @throws IOException
     */
    public static void write(URI path, JSONObject obj) throws IOException {
        FileUtils.writeStringToFile(new File(path), obj.toJSONString(), "UTF-8");
    }

    /**
     * Returns a JSON object from a file.
     * @param path is the file path as String
     * @return the JSONObject of the file.
     * @throws IOException
     * @throws ParseException
     */
    public static JSONArray read(String path) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        String fileObj = IOUtils.readLines(new FileInputStream(path), "UTF-8").toString();
        return (JSONArray) parser.parse(fileObj);
    }

    /**
     * Returns a JSON object from a file.
     * @param file is the file
     * @return the JSONObject of the file.
     * @throws IOException
     * @throws ParseException
     */
    public static JSONArray read(File file) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        String fileObj = IOUtils.readLines(new FileInputStream(file), "UTF-8").toString();
        return (JSONArray) parser.parse(fileObj);
    }

    /**
     * Returns a JSON object from a file.
     * @param path is the file path as URI
     * @return the JSONObject of the file.
     * @throws IOException
     * @throws ParseException
     */
    public static JSONArray read(URI path) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        String fileObj = IOUtils.readLines(new FileInputStream(IOUtils.toString(path, "UTF-8")), "UTF-8").toString();
        return (JSONArray) parser.parse(fileObj);
    }

    /**
     * Returns the first object of a file.
     * @param path is the file path as String.
     * @return return the first JSONObject.
     * @throws IOException
     * @throws ParseException
     */
    public static JSONObject readFirst(String path) throws IOException, ParseException {
        return (JSONObject) read(path).get(0);
    }

    /**
     * Returns the first object of a file.
     * @param file is the file.
     * @return return the first JSONObject.
     * @throws IOException
     * @throws ParseException
     */
    public static JSONObject readFirst(File file) throws IOException, ParseException {
        return (JSONObject) read(file).get(0);
    }

    /**
     * Returns the first object of a file.
     * @param path is the file path as URI.
     * @return return the first JSONObject.
     * @throws IOException
     * @throws ParseException
     */
    public static JSONObject readFirst(URI path) throws IOException, ParseException {
        return (JSONObject) read(path).get(0);
    }

    /**
     * Returns last object of the file.
     * @param path is the file path as String.
     * @return the last JSONObject of the file.
     * @throws IOException
     * @throws ParseException
     */
    public static JSONObject readLast(String path) throws IOException, ParseException {
        JSONArray array = read(path);
        return (JSONObject) array.get(array.size() - 1);
    }

    /**
     * Returns last object of the file.
     * @param file is the file.
     * @return the last JSONObject of the file.
     * @throws IOException
     * @throws ParseException
     */
    public static JSONObject readLast(File file) throws IOException, ParseException {
        JSONArray array = read(file);
        return (JSONObject) array.get(array.size() - 1);
    }

    /**
     * Returns last object of the file.
     * @param path is the file path as URI.
     * @return the last JSONObject of the file.
     * @throws IOException
     * @throws ParseException
     */
    public static JSONObject readLast(URI path) throws IOException, ParseException {
        JSONArray array = read(path);
        return (JSONObject) array.get(array.size() - 1);
    }

    /**
     * Append data to a JSON file.
     * @param path is the file path as String.
     * @param obj the JSONObject to append.
     * @throws ParseException
     * @throws IOException
     */
    public static void append(String path, JSONObject obj) throws ParseException, IOException {
        JSONObject firstObj = readLast(path);
        String fusionStr = firstObj.toJSONString() + obj.toJSONString();
        JSONParser parser = new JSONParser();
        JSONObject fusionObj = (JSONObject) parser.parse(fusionStr);
        write(path, fusionObj);
    }

    /**
     * Append data to a JSON file.
     * @param file is the file path.
     * @param obj the JSONObject to append.
     * @throws ParseException
     * @throws IOException
     */
    public static void append(File file, JSONObject obj) throws ParseException, IOException {
        JSONObject firstObj = readLast(file);
        String fusionStr = firstObj.toJSONString() + obj.toJSONString();
        JSONParser parser = new JSONParser();
        JSONObject fusionObj = (JSONObject) parser.parse(fusionStr);
        write(file, fusionObj);
    }

    /**
     * Append data to a JSON file.
     * @param path is the file path as URI.
     * @param obj the JSONObject to append.
     * @throws ParseException
     * @throws IOException
     */
    public static void append(URI path, JSONObject obj) throws ParseException, IOException {
        JSONObject firstObj = readLast(path);
        String fusionStr = firstObj.toJSONString() + obj.toJSONString();
        JSONParser parser = new JSONParser();
        JSONObject fusionObj = (JSONObject) parser.parse(fusionStr);
        write(path, fusionObj);
    }

}
