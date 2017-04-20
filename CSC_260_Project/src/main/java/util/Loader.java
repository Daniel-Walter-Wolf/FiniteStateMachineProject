package util;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;

import java.util.LinkedList;

public class Loader
{
	/**
	* http://www.javapractices.com/topic/TopicAction.do?Id=57
	*
	* I don't have enough practice to deal with exceptions here so I'm just going
	* to return null if anything goes wrong and the MainWindow will know that
	* that's a sign that the file didn't load.
	*/
	public static Object[] load(String fileName)
	{
		Object[] loadedObjects = new Object[2];

		try{
			InputStream file = new FileInputStream(fileName+".ser");
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);//This is throwing the error
			loadedObjects[0] = input.readObject();
			loadedObjects[1] = input.readObject();
			input.close();
		}
		catch(IOException e)
		{
			e.printStackTrace(System.out);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace(System.out);
		}

		return loadedObjects;
	}

	public static String[] getAvailableFileNames()
	{

		//We won't actually return a linked list object
		// but we don't know how many elements there are yet		
		LinkedList<String> fileStrings = new LinkedList<String>();
		int fileCount = 0;

		//Open the current directory to see what files are available to load
		File file = new File(System.getProperty("user.dir"));
		File[] files = file.listFiles();

		String fileString;
		String fileEnding;
		
		for (int i = 0; i < files.length; i++) {
			fileString = files[i].toString();
			fileEnding = fileString.substring(fileString.length()-4,fileString.length());

			if (fileEnding.equals(".ser"))
			{
				fileStrings.add(fileString.substring(fileString.lastIndexOf('\\')+1,fileString.length()-4));
				fileCount++;
			}
		}

		if (fileCount==0)
		{
			return null;
		}

		String[] returnStrings = new String[fileCount];

		for (int i=0; i<returnStrings.length; i++)
		{
			returnStrings[i]=fileStrings.getFirst();
			fileStrings.removeFirst();
		}

		return returnStrings;
	}
}