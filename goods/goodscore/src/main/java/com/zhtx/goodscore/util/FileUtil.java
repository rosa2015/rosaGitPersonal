package com.zhtx.goodscore.util;


import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 文件处理辅助类
 */
public class FileUtil {

    /**
     * 当前目录路径
     */
    public static String currentWorkDir = System.getProperty("user.dir") + "\\";

    /**
     * 左填充
     *
     * @param str
     * @param length
     * @param ch
     * @return
     */
    public static String leftPad(String str, int length, char ch) {
        if (str.length() >= length) {
            return str;
        }
        char[] chs = new char[length];
        Arrays.fill(chs, ch);
        char[] src = str.toCharArray();
        System.arraycopy(src, 0, chs, length - src.length, src.length);
        return new String(chs);

    }

    /**
     * 删除文件
     *
     * @param fileName
     *            待删除的完整文件名
     * @return
     */
    public static boolean delete(String fileName) {
        boolean result = false;
        File f = new File(fileName);
        if (f.exists()) {
            result = f.delete();

        } else {
            result = true;
        }
        return result;
    }

    /***
     * 递归获取指定目录下的所有的文件（不包括文件夹）
     *
     * @return
     */
    public static ArrayList<File> getAllFiles(String dirPath) {
        File dir = new File(dirPath);

        ArrayList<File> files = new ArrayList<File>();

        if (dir.isDirectory()) {
            File[] fileArr = dir.listFiles();
            for (int i = 0; i < fileArr.length; i++) {
                File f = fileArr[i];
                if (f.isFile()) {
                    files.add(f);
                } else {
                    files.addAll(getAllFiles(f.getPath()));
                }
            }
        }
        return files;
    }

    /**
     * 获取指定目录下的所有文件(不包括子文件夹)
     *
     * @param dirPath
     * @return
     */
    public static ArrayList<File> getDirFiles(String dirPath) {
        File path = new File(dirPath);
        File[] fileArr = path.listFiles();
        ArrayList<File> files = new ArrayList<File>();

        for (File f : fileArr) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }

    /**
     * 获取指定目录下特定文件后缀名的文件列表(不包括子文件夹)
     *
     * @param dirPath
     *            目录路径
     * @param suffix
     *            文件后缀
     * @return
     */
    public static ArrayList<File> getDirFiles(String dirPath,
                                              final String suffix) {
        File path = new File(dirPath);
        File[] fileArr = path.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowerName = name.toLowerCase();
                String lowerSuffix = suffix.toLowerCase();
                if (lowerName.endsWith(lowerSuffix)) {
                    return true;
                }
                return false;
            }

        });
        ArrayList<File> files = new ArrayList<File>();

        for (File f : fileArr) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }

    /**
     * 读取文件内容
     *
     * @param fileName
     *            待读取的完整文件名
     * @return 文件内容
     * @throws IOException
     */
    public static String read(String fileName) throws IOException {
        File f = new File(fileName);
        FileInputStream fs = new FileInputStream(f);
        String result = null;
        byte[] b = new byte[fs.available()];
        fs.read(b);
        fs.close();
        result = new String(b);
        return result;
    }

    /**
     * 写文件
     *
     * @param fileName
     *            目标文件名
     * @param fileContent
     *            写入的内容
     * @return
     * @throws IOException
     */
    public static boolean write(String fileName, String fileContent)
            throws IOException {
        return write(fileName, fileContent, true, true);
    }

    /**
     * 写文件
     *
     * @param fileName
     *            完整文件名(类似：/usr/a/b/c/d.txt)
     * @param fileContent
     *            文件内容
     * @param autoCreateDir
     *            目录不存在时，是否自动创建(多级)目录
     * @return
     * @throws IOException
     */
    public static boolean write(String fileName, String fileContent,
                                boolean autoCreateDir, boolean autoOverwrite) throws IOException {
        return write(fileName, fileContent.getBytes(), autoCreateDir,
                autoOverwrite);
    }

    /**
     * 写文件
     *
     * @param fileName
     *            完整文件名(类似：/usr/a/b/c/d.txt)
     * @param contentBytes
     *            文件内容的字节数组
     * @param autoCreateDir
     *            目录不存在时，是否自动创建(多级)目录
     * @return
     * @throws IOException
     */
    public static boolean write(String fileName, byte[] contentBytes, boolean autoCreateDir, boolean autoOverwrite) throws IOException {
        boolean result = false;
        if (autoCreateDir) {
            createDirs(fileName);
        }
        if (autoOverwrite) {
            delete(fileName);
        }
        File f = new File(fileName);
        FileOutputStream fs = new FileOutputStream(f);
        fs.write(contentBytes);
        fs.flush();
        fs.close();
        result = true;
        return result;
    }

    /**
     * 追加内容到指定文件
     *
     * @param fileName
     * @param fileContent
     * @return
     * @throws IOException
     */
    public static boolean append(String fileName, String fileContent)
            throws IOException {
        boolean result = false;
        File f = new File(fileName);
        if (f.exists()) {
            RandomAccessFile rFile = new RandomAccessFile(f, "rw");
            byte[] b = fileContent.getBytes();
            long originLen = f.length();
            rFile.setLength(originLen + b.length);
            rFile.seek(originLen);
            rFile.write(b);
            rFile.close();
        }
        result = true;
        return result;
    }

    /**
     * 拆分文件
     *
     * @param fileName
     *            待拆分的完整文件名
     * @param byteSize
     *            按多少字节大小拆分
     * @return 拆分后的文件名列表
     * @throws IOException
     */
    public List<String> splitBySize(String fileName, int byteSize)
            throws IOException {
        List<String> parts = new ArrayList<String>();
        File file = new File(fileName);
        int count = (int) Math.ceil(file.length() / (double) byteSize);
        int countLen = (count + "").length();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(count, count * 3, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(count * 2));

        for (int i = 0; i < count; i++) {
            String partFileName = file.getPath() + "." + leftPad((i + 1) + "", countLen, '0') + ".part";
            threadPool.execute(new SplitRunnable(byteSize, i * byteSize, partFileName, file));
            parts.add(partFileName);
        }
        return parts;
    }

    /**
     * 合并文件
     *
     * @param dirPath
     *            拆分文件所在目录名
     * @param partFileSuffix
     *            拆分文件后缀名
     * @param partFileSize
     *            拆分文件的字节数大小
     * @param mergeFileName
     *            合并后的文件名
     * @throws IOException
     */
    public void mergePartFiles(String dirPath, String partFileSuffix, int partFileSize, String mergeFileName) throws IOException {
        ArrayList<File> partFiles = FileUtil.getDirFiles(dirPath, partFileSuffix);
        Collections.sort(partFiles, new FileComparator());

        RandomAccessFile randomAccessFile = new RandomAccessFile(mergeFileName, "rw");
        randomAccessFile.setLength(partFileSize * (partFiles.size() - 1) + partFiles.get(partFiles.size() - 1).length());
        randomAccessFile.close();

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(partFiles.size(), partFiles.size() * 3, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(partFiles.size() * 2));

        for (int i = 0; i < partFiles.size(); i++) {
            threadPool.execute(new MergeRunnable(i * partFileSize, mergeFileName, partFiles.get(i)));
        }

    }

    /**
     * 根据文件名，比较文件
     *
     * @author yjmyzz@126.com
     *
     */
    private class FileComparator implements Comparator<File> {
        public int compare(File o1, File o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }

    /**
     * 创建(多级)目录
     *
     * @param filePath
     *            完整的文件名(类似：/usr/a/b/c/d.xml)
     */
    public static void createDirs(String filePath) {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

    }

    /**
     * 分割处理Runnable
     */
    private class SplitRunnable implements Runnable {
        int byteSize;
        String partFileName;
        File originFile;
        int startPos;

        public SplitRunnable(int byteSize, int startPos, String partFileName,
                             File originFile) {
            this.startPos = startPos;
            this.byteSize = byteSize;
            this.partFileName = partFileName;
            this.originFile = originFile;
        }

        public void run() {
            RandomAccessFile rFile;
            OutputStream os;
            try {
                rFile = new RandomAccessFile(originFile, "r");
                byte[] b = new byte[byteSize];
                rFile.seek(startPos);// 移动指针到每“段”开头
                int s = rFile.read(b);
                os = new FileOutputStream(partFileName);
                os.write(b, 0, s);
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 合并处理Runnable
     */
    private class MergeRunnable implements Runnable {
        long startPos;
        String mergeFileName;
        File partFile;

        public MergeRunnable(long startPos, String mergeFileName, File partFile) {
            this.startPos = startPos;
            this.mergeFileName = mergeFileName;
            this.partFile = partFile;
        }

        public void run() {
            RandomAccessFile rFile;
            try {
                rFile = new RandomAccessFile(mergeFileName, "rw");
                rFile.seek(startPos);
                FileInputStream fs = new FileInputStream(partFile);
                byte[] b = new byte[fs.available()];
                fs.read(b);
                fs.close();
                rFile.write(b);
                rFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件下载
     * @param path
     * @param response
     */
    public static void download(String path, HttpServletResponse response) {
        OutputStream toClient = null;
        InputStream fis = null;
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
//            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
//            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("GBK"), "ISO8859_1"));
            response.addHeader("Content-Length", "" + file.length());
//            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            toClient.write(buffer);
//            toClient.flush();
//            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fis.close();
                toClient.flush();
                toClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    
    
    /** 
     * 新建目录. 
     *  
     * @param path 文件路径 
     * @throws Exception 
     */  
    public static void createDirectory(String path) throws Exception {  
        if (StringUtils.isEmpty(path)) {  
            return;  
        }  
        try {  
            // 获得文件对象  
            File f = new File(path);  
            if (!f.exists()) {  
                // 如果路径不存在,则创建  
                f.mkdirs();  
            }  
        } catch (Exception e) {  
           
            throw e;  
        }  
    }  
  
    /** 
     * 新建文件. 
     *  
     * @param path 文件路径 
     * @throws Exception 
     */  
    public static void createFile(String path) throws Exception {  
        if (StringUtils.isEmpty(path)) {  
            return;  
        }  
        try {  
            // 获得文件对象  
            File f = new File(path);  
            if (f.exists()) {  
                return;  
            }  
            // 如果路径不存在,则创建  
            if (!f.getParentFile().exists()) {  
                f.getParentFile().mkdirs();  
            }  
            f.createNewFile();  
        } catch (Exception e) {                
            throw e;  
        }  
    }   

  

}