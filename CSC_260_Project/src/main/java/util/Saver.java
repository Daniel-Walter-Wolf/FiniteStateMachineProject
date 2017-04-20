package util;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectOutput;

import java.io.IOException;
import java.io.FileNotFoundException;

public class Saver
{
	/**
	* http://www.javapractices.com/topic/TopicAction.do?Id=57
	*/
	public static boolean save(Object object1, Object object2, String fileName)
	{

		if (!validFileName(fileName))
		{
			return false;
		}
		try
		{
			OutputStream file = new FileOutputStream(fileName+".ser");
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);

			output.writeObject(object1);
			output.writeObject(object2);
			output.close();
			return true;
		}
		catch(IOException e)
		{
			e.printStackTrace(System.out);
			return false;
		}
	}

	private static boolean validFileName(String fileName)
	{
		return !(fileName.contains("\\") || fileName.contains("."));
	}
}