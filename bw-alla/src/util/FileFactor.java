/*
 * FileFactor.java
 *
 * Created on March 23, 2003, 5:01 PM
 */

package util;

import java.io.*;
import java.util.Vector;
import bwexception.BeanWatcherException;

/**
 * This program is to factor file and used for manipulate
 * operation wich files. All methods are statics
 * @author  alla
 * @version 0.0.0.2
 */
public class FileFactor {
    
    /** This method is to create a new file
     * @param _pathName is the path name for creat the new file
     * @return the new file created
     */
    public static File creatFile(String _pathName){
        File fileResult;
        fileResult = new File(_pathName);
        return fileResult;
    }
    
    /**
     * This method is to make new dir
     * @param _path is the path name for creat the new dir
     */
    public static boolean makeDir(String _path){
        File file = new File(_path);
        return file.mkdir();
        
    }
    
    /** This method is to remove a file from string pathName
     * @param _pathName is the path name for file removed
     * @return true if remove sucess, and false if not
     */
    public static boolean removeFile(String _pathName){
        File file;
        file = new File(_pathName);
        
        return file.delete();
    }
    
    /** This method copy one file from other
     * @param _src is the file source
     * @param _dst is the file destin
     */
    public static void copyFile(File _src, File _dst){
        try{
            FileWriter fw     = new FileWriter(_dst);
            FileReader fr     = new FileReader(_src);
            BufferedReader br = new BufferedReader(fr);
            
            boolean eof = false;
            while (!eof) {
                String line = br.readLine();
                if (line == null) {
                    eof = true;
                    continue;
                } else {
                    fw.write(line+"\n");
                    fw.flush();
                }
            }
            br.close();
            fr.close();
            fw.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    /** This method copy one file from other
     * @param _srcPath is the path for file source
     * @param _dstPath is the path for file destin
     */
    public static void copyFile(String _srcPath, String _dstPath){
        try{
            FileWriter fw = new FileWriter(_dstPath);
            FileReader fr = new FileReader(_srcPath);
            BufferedReader br = new BufferedReader(fr);
            
            boolean eof = false;
            while (!eof) {
                String line = br.readLine();
                if (line == null) {
                    eof = true;
                    continue;
                } else {
                    fw.write(line+"\n");
                    fw.flush();
                }
            }
            br.close();
            fr.close();
            fw.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static void copyDir(String _dirSrc, String _dirDst){
        int size;
        String files[];
        File   dir;
        File   dirDst;
        
        try {
            // Inicialize variables
            dir = new File(_dirSrc);
            if(!dir.exists()){
                new BeanWatcherException("Dir "+_dirSrc+" not found!");
            }
            
            dirDst = new File(_dirDst);
            if(!dirDst.mkdirs()){
                new BeanWatcherException("Dir "+_dirDst+" not found!");
            }
            
            // Get list of components in director and sort names
            files = dir.list();
            size = files.length;
            for(int i = 0; i < size; i++){
                copyFile(_dirSrc + files[i], _dirDst + files[i] +Config.getExtension());
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /** This method is to read text from file
     * @param _filePath is the file name read
     * @return the content of file
     */
    public static String readFile(String _filePath){
        boolean eof;
        BufferedReader bufferFile;
        FileReader     pointerFile;
        String         line;
        StringBuffer   buffer;
        
        buffer = new StringBuffer("");
        try{
            // Inicialize the variables used
            pointerFile = new FileReader(_filePath);
            bufferFile  = new BufferedReader(pointerFile);
            
            // Loop for cover all file
            eof = false;
            while (!eof) {
                line = bufferFile.readLine();
                if(line == null){
                    eof = true;
                } else {
                    buffer.append(line);
                    buffer.append("\n");
                }
            }
            bufferFile.close();
            return buffer.toString();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    /** This method is to write in file
     * @param _filePath is the path from write in file
     * @param _text is the content for write
     */
    public static void writeFile(String _filePath, String _text){
        try{
            FileWriter fw = new FileWriter(_filePath);
            fw.write(_text+"\n");
            fw.flush();
            fw.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    /** This method is to list file from directory
     * @param _dirPath is the directory from list
     * @param _token is the extension files for to list
     * @return the vector wich list sorted
     */
    public static Vector<String> listFiles(String _dirPath, String _token){
        int      i;
        Vector<String>  result;
        String[] files;
        File     dir;
        
        result = new Vector<String>();
        try {
            // Inicialize variables
            dir = new File(_dirPath);
            if(!dir.exists()){
                new BeanWatcherException("Dir not found!");
            }
            
            // Get list of components in director and sort names
            files = dir.list();
            files = sort(files);
            
            
            // Loop for add components in vector with all components name
            for (i = 0; i < files.length; i++) {
                // Condiction for *.java component and cut extension
                if (files[i].endsWith(_token)) {
                    files[i] = files[i].substring(0,files[i].length()-5);
                    result.addElement(files[i]);
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    /** This method is to list file from directory
     * @param _dirPath is the directory from list
     * @param _token is the extension files for to list
     * @return the vector wich list sorted
     */
    public static String[] listFilesString(String _dirPath, String _token){
        String[] files = null;
        File     dir;
        
        try {
            // Inicialize variables
            dir = new File(_dirPath);
            if(!dir.exists()){
                new BeanWatcherException("Dir not found!");
            }
            
            // Get list of components in director and sort names
            files = dir.list();
            files = sort(files);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return files;
    }
    
    /**
     *
     */
    public static boolean hasFile(String _path){
        File file = new File(_path);
        return file.exists();
    }
    
    /** This method is to sort a list of strings
     * @param A list of words for sort
     * @return return list of words sorted
     */
    private static String[] sort(String[] A) {
        int i, j, Min, n;
        String x;
        
        n = A.length;
        
        for (i = 0; i < n-1; i++) {
            Min = i;
            for (j = i + 1; j < n; j++) {
                if (A[j].compareTo(A[Min])<0) Min = j;
            }
            x = A[Min];
            A[Min] = A[i];
            A[i] = x;
        }
        return A;
    }
}
