package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ImageModelJSON {

	/**
	 * readImageFromJson permet de lire le contenu d'un fichier .json dont le nom est le parametre file_Name et d'en renvoyer le contenu
	 * @param file_Name
	 * String qui correspond au nom du fichier .json
	 * @return
	 * String[] res qui renvoie le contenu du fichier .json : le chemin du fichier, le nom du fichier, la clef et le String correspondant au tableau de Bytes cryptes  
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public String[] readImageFromJson(String file_Name) throws FileNotFoundException, IOException, ParseException {
		String[] res = null;
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(
				new FileReader("res-test/" + file_Name.split("\\.")[0] + "_" + file_Name.split("\\.")[1] + ".json"));
		JSONObject jsonObject = (JSONObject) obj;

		String filePath = jsonObject.get("filePath").toString();
		res[res.length] = filePath;
		String fileName = jsonObject.get("fileName").toString();
		res[res.length] = fileName;
		String key = jsonObject.get("clef").toString();
		res[res.length] = key;
		String encryptedString = jsonObject.get("encryptedString").toString();
		res[res.length] = encryptedString;

		System.out.println("Successfully reading JSON Object...");
		System.out.println("\nJSON Object: " + obj);
		return res;
	}
	
	/**
	 * writeImageModelJSONFile cree un fichier .json contenant le chemin du fichier, le nom du fichier, la clef et le Dtring correspondant au tableau de Bytes crptes
	 * @param filePath
	 * String correspondant au chemin du fichier
	 * @param fileName
	 * String correspondant au nom du fichier
	 * @param key
	 * String correspondant a la clef pour l'algorithme de cryptage
	 * @param encryptedString
	 * String correspondant au tableau de Bytes cryptes
	 * @throws IOException
	 */
	public void writeImageModelJSONFile(String filePath, String fileName, String key, String encryptedString)
			throws IOException {
		JSONObject obj = new JSONObject();
		obj.put("filePath", filePath);
		obj.put("fileName", fileName);
		obj.put("clef", key);
		obj.put("encryptedString", encryptedString);

		// try-with-resources statement based on post comment below :)
		try (FileWriter file = new FileWriter(
				"res-test/" + fileName.split("\\.")[0] + "_" + fileName.split("\\.")[1] + ".json")) {
			file.write(obj.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);
		}
	}
}