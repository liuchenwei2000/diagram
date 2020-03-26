import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

/**
 * <p>
 * <p>
 * Created by liuchenwei on 2019-09-26.
 */
public class LineCounter {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String fileDir = args[0];
        long start = System.currentTimeMillis();
        try {
            counter(new File(fileDir));
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("total time : " + (end - start)/1000.0 + "s");
        System.out.println("total files：" + files);
        System.out.println("total lines：" + lines);
    }

    private static int lines = 0;
    private static int files = 0;

    private static FileFilter java_filter = new FileFilter(){

        @Override
        public boolean accept(File file) {
            return file.isDirectory() || file.getName().toLowerCase().endsWith(".java");
        }};

    private static void counter(File file) throws IOException{
        if(file.isFile()){
            files++;
            readJavaFile(file);
        }else {
            File[] javaFiles = file.listFiles(java_filter);
            for (File jfile : javaFiles) {
                counter(jfile);
            }
        }
    }

    private static void readJavaFile(File file) throws IOException{
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String s = null;
            while((s = br.readLine()) != null){
                s = s.trim();
                boolean isSourceCode = (s.length()>1) &&
                        (!s.startsWith("*")) && (!s.startsWith("//"));
                if(isSourceCode)
                {
                    lines++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(br != null) {
                br.close();
            }
        }
    }
}
