package com.booxj.gp.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 普通IO，FileChannel(文件通道)，MMAP(内存映射)
 *
 * @author booxj
 * @create 2019/7/12 15:30
 * @since
 */
public class FileUtils {


    public void FileChannelTest() throws IOException {

        FileChannel fileChannel = new RandomAccessFile(new File(""), "rw").getChannel();

        // 写
        byte[] data = new byte[4096];
        long position = 1024L;
        //指定 position 写入 4kb 的数据
        fileChannel.write(ByteBuffer.wrap(data), position);
        //从当前文件指针的位置写入 4kb 的数据
        fileChannel.write(ByteBuffer.wrap(data));

        // 读
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        position = 1024L;
        //指定 position 读取 4kb 的数据
        fileChannel.read(buffer, position);
        //从当前文件指针的位置读取 4kb 的数据
        fileChannel.read(buffer);
    }
}
