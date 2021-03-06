package the.bytecode.club.bytecodeviewer.util;

import the.bytecode.club.bytecodeviewer.BytecodeViewer;
import the.bytecode.club.bytecodeviewer.Configuration;
import the.bytecode.club.bytecodeviewer.gui.components.FileChooser;
import the.bytecode.club.bytecodeviewer.gui.components.MultipleChoiceDialogue;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/***************************************************************************
 * Bytecode Viewer (BCV) - Java & Android Reverse Engineering Suite        *
 * Copyright (C) 2014 Kalen 'Konloch' Kinloch - http://bytecodeviewer.com  *
 *                                                                         *
 * This program is free software: you can redistribute it and/or modify    *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation, either version 3 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>. *
 ***************************************************************************/

/**
 * @author Konloch
 * @since 7/1/2021
 */
public class DialogueUtils
{
	/**
	 * Asks if the user would like to overwrite the file
	 */
	public static boolean canOverwriteFile(String filePath)
	{
		return canOverwriteFile(new File(filePath));
	}
	
	/**
	 * Asks if the user would like to overwrite the file
	 */
	public static boolean canOverwriteFile(File file) {
		if (file.exists())
		{
			MultipleChoiceDialogue dialogue = new MultipleChoiceDialogue("Bytecode Viewer - Overwrite File",
					"Are you sure you wish to overwrite this existing file?",
					new String[]{"Yes", "No"});
			
			if (dialogue.promptChoice() == 0) {
				file.delete();
				
				return true;
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Prompts a File Chooser dilogue
	 */
	public static File fileChooser(String title, String description, String... extensions)
	{
		return fileChooser(title, description, null, extensions);
	}
	
	/**
	 * Prompts a File Chooser dilogue
	 */
	public static File fileChooser(String title, String description, FileFilter filter, String... extensions)
	{
		final JFileChooser fc = new FileChooser(Configuration.getLastDirectory(),
				title,
				description,
				extensions);
		
		if(filter != null)
			fc.setFileFilter(filter);
		
		int returnVal = fc.showOpenDialog(BytecodeViewer.viewer);
		if (returnVal == JFileChooser.APPROVE_OPTION)
			try {
				File file = fc.getSelectedFile();
				Configuration.lastDirectory = file.getAbsolutePath();
				return file;
			} catch (Exception e1) {
				BytecodeViewer.handleException(e1);
			}
		
		return null;
	}
}
