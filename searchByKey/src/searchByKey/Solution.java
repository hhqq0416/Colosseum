package searchByKey;
import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.PrintStream;  
//import java.io.FileReader;  

public class Solution{  
    // file info  
    //private static StringBuffer fileInfo;  
    // dir info  
    //private static StringBuffer dirInfo;  
    // child file info  
   // private static File[] fm;  
	
	
    /** 
     * main 
     *  
     * @param args 
     */  
    public static void main (String[] args) throws IOException {  
		String dir0 = "F:\\科林合同\\科林电气合同扫描件\\2012年合同扫描件";
		String dir1 = "F:\\科林合同\\10big";
		
       
        //File dir = new File(dir0);
		BufferedReader br = new BufferedReader(new InputStreamReader(  
                new FileInputStream("F:\\科林合同\\科林电气合同扫描件\\2012年合同扫描件\\aaa.txt"))); 
		//printAllInfo(dir);		

		for (String line = br.readLine(); line != null; line = br.readLine()) {		
		    
			String key = line;  
			  
			System.out.println("\nThe key is : " + key);  
			System.out.println("The result is: ");  
			traverseFolder(dir0,dir1,key);
		}
		br.close();		
    }  
    
    public static void traverseFolder(String path,String pathTo,String key) {  
    	String temp0,temp1;  
        File file = new File(path);  
        if (file.exists()) {  
            File[] files = file.listFiles();  
            if (files.length == 0) {  
                System.out.println("文件夹是空的!");  
                return;  
            } else {  
                for (File file2 : files) { 
                	boolean found = false;
                	if (file2.isDirectory()&&!found) {  
                        //System.out.println("文件夹:" + file2.getAbsolutePath()); 
                    	if (file2.getName().indexOf(key) >= 0) {  
            				System.out.println("Copied file: " + file2.getName() + "   ");
        					temp0 = file2.getAbsolutePath();
        					temp1 = pathTo+"\\"+key;
        					copy(temp0,temp1);  
        					System.out.println("Copy successed!");
        					found=true;
        					break;
            			}
                    	traverseFolder(file2.getAbsolutePath(),pathTo,key);  
                    } 
                }  
            }  
        } else {  
            System.out.println("文件不存在!");  
        }  
    } 
    
	
	private static void copy(String src, String des) {  
        File file1=new File(src);  
        File[] fs=file1.listFiles();  
        File file2=new File(des);  
        if(!file2.exists()){  
            file2.mkdirs();  
        }  
        for (File f : fs) {  
            if(f.isFile()){  
                fileCopy(f.getPath(),des+"\\"+f.getName()); 
            }else if(f.isDirectory()){  
                copy(f.getPath(),des+"\\"+f.getName());  
            }  
        }  
          
    }  
	
	
	private static void fileCopy(String src, String des) {  
      
        BufferedReader br=null;  
        PrintStream ps=null;  
          
        try {  
            br=new BufferedReader(new InputStreamReader(new FileInputStream(src)));  
            ps=new PrintStream(new FileOutputStream(des));  
            String s=null;  
            while((s=br.readLine())!=null){  
                ps.println(s);  
                ps.flush();  
            }  
              
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }finally{  
              
                try {  
                    if(br!=null)  br.close();  
                    if(ps!=null)  ps.close();  
                } catch (IOException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
        }  
    }  
}  
