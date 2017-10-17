package bio;

import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;

/**
 * @author liyebing created on 17/2/14.
 * @version $Id$
 */
public class FileTest {


    /**
     * 文件路径
     *
     * @throws IOException
     */
    @Test
    public void testPathInfo() throws IOException {
        File file = new File("FileTest.java");
        //绝对路径
        System.out.println("Absolute path = " + file.getAbsolutePath());
        //规范路径
        System.out.println("Canonical path = " + file.getCanonicalPath());
        //文件名称
        System.out.println("Name = " + file.getName());
        //路径名称
        System.out.println("Path = " + file.getPath());
        //判断文件路径是否是绝对路径
        System.out.println("Is absolute = " + file.isAbsolute());
    }


    /**
     * 文件目录相关
     *
     * @throws IOException
     */
    @Test
    public void testFileDirectoryInfo() throws IOException {
        File file = new File("/Users/liyebing");
        //是否存在
        System.out.println("Exists = " + file.exists());
        //是否是目录
        System.out.println("Is directory = " + file.isDirectory());
        //是否是文件
        System.out.println("Is file = " + file.isFile());
        //是否是隐藏文件
        System.out.println("Is hidden = " + file.isHidden());
        //文件最后修改日期
        System.out.println("Last modified = " + new Date(file.lastModified()));
        //文件长度
        System.out.println("Length = " + file.length());
    }


    @Test
    public void testPartitionSpace() {
        //获取文件系统根目录
        File[] roots = File.listRoots();
        for (File root : roots) {
            //获取文件目录剩余空间
            System.out.println("Free space on this partition = " + root.getFreeSpace());
            //获取文件目录可用空间
            System.out.println("Usable space on this partition = " + root.getUsableSpace());
            //获取文件目录总空间
            System.out.println("Total space on this partition = " + root.getTotalSpace());
        }
    }

    @Test
    public void testDir() {
        File file = new File("/Users/liyebing/tech_dir/study-example/src/main/java/com/example/classicio/");
        //使用文件名称过滤器获取文件列表
        FilenameFilter fnf = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".java");
            }
        };
        String[] names = file.list(fnf);
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void testModifyDir() throws IOException {
        File f = new File("1.txt");
        if (!f.exists()) {
            //创建文件
            f.createNewFile();
        }
        //删除文件
        f.delete();
    }


    @Test
    public void testPermissions() throws IOException {
        File file = new File("1.txt");
        //修改权限
        file.setExecutable(false);
        file.setReadable(true);

        //读取权限
        System.out.println("Checking permissions for 1.txt");
        System.out.println(" Execute = " + file.canExecute());
        System.out.println(" Read = " + file.canRead());
        System.out.println(" Write = " + file.canWrite());
    }
}
