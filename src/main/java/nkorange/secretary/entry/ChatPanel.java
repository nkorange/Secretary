package nkorange.secretary.entry;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * JTextPane的例子，模拟聊天客户端，演示了为每段文字设置字体、字号、样式、颜色、背景色和插入图片功能
 * @author 五斗米 <如转载请保留作者和出处>
 * @blog http://blog.csdn.net/mq612
 */

public class ChatPanel extends JFrame {

    private static final long serialVersionUID = -2397593626990759111L;

    private JScrollPane scrollPane = null; // 滚动

    private JTextPane text = null; // 不用说了，如果不认识的话就没必要往后看了

    private Box box = null; // 放输入组件的容器

    private JButton b_insert = null, b_remove = null, b_icon = null, b_record = null; // 插入按钮;清除按钮;插入图片按钮

    private volatile boolean talkStatus = false;

    private JTextField addText = null; // 文字输入框

    private JComboBox fontName = null, fontSize = null, fontStyle = null, fontColor = null,
            fontBackColor = null; // 字体名称;字号大小;文字样式;文字颜色;文字背景颜色

    private StyledDocument doc = null; // 非常重要插入文字样式就靠它了

    private TargetDataLine line;

    private File file;

    private String errStr;

    String fileName = "untitled";

    double duration, seconds;

    Capture capture = new Capture();

    AudioInputStream audioInputStream;

    private void stopTalk() {

        talkStatus = false;
        b_record.setText("说话");
    }

    private void stopTalk(String msg) {

        talkStatus = false;
        b_record.setText("说话");
    }

    private void reportStatus(String msg) {
        if ((errStr = msg) != null) {
            System.out.println(errStr);
        }
    }

    public void createAudioInputStream(File file, boolean updateComponents) {
        if (file != null && file.isFile()) {
            try {
                this.file = file;
                errStr = null;
                audioInputStream = AudioSystem.getAudioInputStream(file);
                fileName = file.getName();
                long milliseconds = (long) ((audioInputStream.getFrameLength() * 1000) / audioInputStream
                        .getFormat().getFrameRate());
                duration = milliseconds / 1000.0;
                if (updateComponents) {
                }
            } catch (Exception ex) {
                reportStatus(ex.toString());
            }
        } else {
            reportStatus("Audio file required.");
        }
    }

    public void saveToFile(String name, AudioFileFormat.Type fileType) {
        if (audioInputStream == null) {
            reportStatus("No loaded audio to save");
            return;
        } else if (file != null) {
            createAudioInputStream(file, false);
        }
        // reset to the beginnning of the captured data
        try {
            audioInputStream.reset();
        } catch (Exception e) {
            reportStatus("Unable to reset stream " + e);
            return;
        }
        File file = new File(fileName = name);
        try {
            if (AudioSystem.write(audioInputStream, fileType, file) == -1) {
                throw new IOException("Problems writing to file");
            }
        } catch (Exception ex) {
            reportStatus(ex.toString());
        }
    }


    class Capture implements Runnable {
        TargetDataLine line;

        Thread thread;

        public void start() {
            errStr = null;
            thread = new Thread(this);
            thread.setName("Capture");
            thread.start();
        }

        public void stop() {
            thread = null;
        }

        private void shutDown(String message) {
            if ((errStr = message) != null && thread != null) {
                thread = null;
                b_record.setText("说话");
                System.err.println(errStr);
            }
        }

        public void run() {
            duration = 0;
            audioInputStream = null;
            // get an AudioInputStream of the desired format for playback
            AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
            // define the required attributes for our line,
            // and make sure a compatible line is supported.
            // float rate = 44100f;
            // int sampleSize = 16;
            // String signedString = "signed";
            // boolean bigEndian = true;
            // int channels = 2;
            float rate = 16000f;
            int sampleSize = 16;
            String signedString = "signed";
            boolean bigEndian = true;
            int channels = 1;
            AudioFormat format = new AudioFormat(encoding, rate, sampleSize,
                    channels, (sampleSize / 8) * channels, rate, bigEndian);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                shutDown("Line matching " + info + " not supported.");
                return;
            }
            // get an AudioInputStream of the desired format for playback
            try {
                line = (TargetDataLine) AudioSystem.getLine(info);
                line.open(format, line.getBufferSize());
            } catch (LineUnavailableException ex) {
                shutDown("Unable to open the line: " + ex);
                return;
            } catch (SecurityException ex) {
                shutDown(ex.toString());
                return;
            } catch (Exception ex) {
                shutDown(ex.toString());
                return;
            }
            // play back the captured audio data
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int frameSizeInBytes = format.getFrameSize();
            int bufferLengthInFrames = line.getBufferSize() / 8;
            int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
            byte[] data = new byte[bufferLengthInBytes];
            int numBytesRead;
            line.start();
            while (thread != null) {
                if ((numBytesRead = line.read(data, 0, bufferLengthInBytes)) == -1) {
                    break;
                }
                out.write(data, 0, numBytesRead);
            }
            // we reached the end of the stream. stop and close the line.
            line.stop();
            line.close();
            line = null;
            // stop and close the output stream
            try {
                out.flush();
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // load bytes into the audio input stream for playback
            byte audioBytes[] = out.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
            audioInputStream = new AudioInputStream(bais, format,
                    audioBytes.length / frameSizeInBytes);
            long milliseconds = (long) ((audioInputStream.getFrameLength() * 1000) / format
                    .getFrameRate());
            duration = milliseconds / 1000.0;
            try {
                audioInputStream.reset();
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
            saveToFile("record22.wav", AudioFileFormat.Type.WAVE);
        }
    }


    public ChatPanel() {
        super("小秘");
        try { // 使用Windows的界面风格
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        text = new JTextPane();
        text.setEditable(false); // 不可录入
        doc = text.getStyledDocument(); // 获得JTextPane的Document
        scrollPane = new JScrollPane(text);
        addText = new JTextField(18);
        String[] str_name = { "宋体", "黑体", "Dialog", "Gulim" };
        String[] str_Size = { "12", "14", "18", "22", "30", "40" };
        String[] str_Style = { "常规", "斜体", "粗体", "粗斜体" };
        String[] str_Color = { "黑色", "红色", "蓝色", "黄色", "绿色" };
        String[] str_BackColor = { "无色", "灰色", "淡红", "淡蓝", "淡黄", "淡绿" };
        fontName = new JComboBox(str_name); // 字体名称
        fontSize = new JComboBox(str_Size); // 字号
        fontStyle = new JComboBox(str_Style); // 样式
        fontColor = new JComboBox(str_Color); // 颜色
        fontBackColor = new JComboBox(str_BackColor); // 背景颜色
        b_insert = new JButton("文字"); // 插入
        b_remove = new JButton("清空"); // 清除
        b_icon = new JButton("图片"); // 插入图片
        b_record = new JButton("说话"); // 说话
        b_insert.addActionListener(new ActionListener() { // 插入文字的事件
            public void actionPerformed(ActionEvent e) {
                insert(getFontAttrib());
                addText.setText("");
            }
        });
        b_remove.addActionListener(new ActionListener() { // 清除事件
            public void actionPerformed(ActionEvent e) {
                text.setText("");
            }
        });
        b_icon.addActionListener(new ActionListener() { // 插入图片事件
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser f = new JFileChooser(); // 查找文件
                f.showOpenDialog(null);
                insertIcon(f.getSelectedFile()); // 插入图片
            }
        });
        b_record.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (b_record.getText().equals("说话")) {
                    b_record.setText("说完了");
                    capture.start();
                } else {
                    b_record.setText("说话");
                    capture.stop();
                }

            }
        });
        box = Box.createVerticalBox(); // 竖结构
        Box box_1 = Box.createHorizontalBox(); // 横结构
        Box box_2 = Box.createHorizontalBox(); // 横结构
        box.add(box_1);
        box.add(Box.createVerticalStrut(8)); // 两行的间距
        box.add(box_2);
        box.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8)); // 8个的边距
        // 开始将所需组件加入容器

        box_2.add(addText);
        box_2.add(Box.createHorizontalStrut(8));
        box_2.add(b_insert);
        box_2.add(Box.createHorizontalStrut(8));
        box_2.add(b_remove);
        box_2.add(Box.createHorizontalStrut(8));
        box_2.add(b_record);
        this.getRootPane().setDefaultButton(b_insert); // 默认回车按钮
        this.getContentPane().add(scrollPane);
        this.getContentPane().add(box, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        addText.requestFocus();
    }

    /**
     * 插入图片
     *
     * @param
     */
    private void insertIcon(File file) {
        text.setCaretPosition(doc.getLength()); // 设置插入位置
        text.insertIcon(new ImageIcon(file.getPath())); // 插入图片
        insert(new FontAttrib()); // 这样做可以换行
    }

    /**
     * 将文本插入JTextPane
     *
     * @param attrib
     */
    private void insert(FontAttrib attrib) {
        try { // 插入文本
            doc.insertString(doc.getLength(), "我：" + attrib.getText() + "\n", attrib.getAttrSet());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所需要的文字设置
     *
     * @return FontAttrib
     */
    private FontAttrib getFontAttrib() {
        FontAttrib att = new FontAttrib();
        att.setText(addText.getText());
        att.setName((String) fontName.getSelectedItem());
        att.setSize(Integer.parseInt((String) fontSize.getSelectedItem()));
        String temp_style = (String) fontStyle.getSelectedItem();
        if (temp_style.equals("常规")) {
            att.setStyle(FontAttrib.GENERAL);
        } else if (temp_style.equals("粗体")) {
            att.setStyle(FontAttrib.BOLD);
        } else if (temp_style.equals("斜体")) {
            att.setStyle(FontAttrib.ITALIC);
        } else if (temp_style.equals("粗斜体")) {
            att.setStyle(FontAttrib.BOLD_ITALIC);
        }
        String temp_color = (String) fontColor.getSelectedItem();
        if (temp_color.equals("黑色")) {
            att.setColor(new Color(0, 0, 0));
        } else if (temp_color.equals("红色")) {
            att.setColor(new Color(255, 0, 0));
        } else if (temp_color.equals("蓝色")) {
            att.setColor(new Color(0, 0, 255));
        } else if (temp_color.equals("黄色")) {
            att.setColor(new Color(255, 255, 0));
        } else if (temp_color.equals("绿色")) {
            att.setColor(new Color(0, 255, 0));
        }
        String temp_backColor = (String) fontBackColor.getSelectedItem();
        if (!temp_backColor.equals("无色")) {
            if (temp_backColor.equals("灰色")) {
                att.setBackColor(new Color(200, 200, 200));
            } else if (temp_backColor.equals("淡红")) {
                att.setBackColor(new Color(255, 200, 200));
            } else if (temp_backColor.equals("淡蓝")) {
                att.setBackColor(new Color(200, 200, 255));
            } else if (temp_backColor.equals("淡黄")) {
                att.setBackColor(new Color(255, 255, 200));
            } else if (temp_backColor.equals("淡绿")) {
                att.setBackColor(new Color(200, 255, 200));
            }
        }
        return att;
    }

    public static void main(String args[]) {
        new ChatPanel();
    }

    /**
     * 字体的属性类
     */
    private class FontAttrib {
        public static final int GENERAL = 0; // 常规

        public static final int BOLD = 1; // 粗体

        public static final int ITALIC = 2; // 斜体

        public static final int BOLD_ITALIC = 3; // 粗斜体

        private SimpleAttributeSet attrSet = null; // 属性集

        private String text = null, name = null; // 要输入的文本和字体名称

        private int style = 0, size = 0; // 样式和字号

        private Color color = null, backColor = null; // 文字颜色和背景颜色

        /**
         * 一个空的构造（可当做换行使用）
         */
        public FontAttrib() {
        }

        /**
         * 返回属性集
         *
         * @return
         */
        public SimpleAttributeSet getAttrSet() {
            attrSet = new SimpleAttributeSet();
            if (name != null)
                StyleConstants.setFontFamily(attrSet, name);
            if (style == FontAttrib.GENERAL) {
                StyleConstants.setBold(attrSet, false);
                StyleConstants.setItalic(attrSet, false);
            } else if (style == FontAttrib.BOLD) {
                StyleConstants.setBold(attrSet, true);
                StyleConstants.setItalic(attrSet, false);
            } else if (style == FontAttrib.ITALIC) {
                StyleConstants.setBold(attrSet, false);
                StyleConstants.setItalic(attrSet, true);
            } else if (style == FontAttrib.BOLD_ITALIC) {
                StyleConstants.setBold(attrSet, true);
                StyleConstants.setItalic(attrSet, true);
            }
            StyleConstants.setFontSize(attrSet, size);
            if (color != null)
                StyleConstants.setForeground(attrSet, color);
            if (backColor != null)
                StyleConstants.setBackground(attrSet, backColor);
            return attrSet;
        }

        /**
         * 设置属性集
         *
         * @param attrSet
         */
        public void setAttrSet(SimpleAttributeSet attrSet) {
            this.attrSet = attrSet;
        }

  /* 后面的注释就不写了，一看就明白 */

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public Color getBackColor() {
            return backColor;
        }

        public void setBackColor(Color backColor) {
            this.backColor = backColor;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getStyle() {
            return style;
        }

        public void setStyle(int style) {
            this.style = style;
        }
    }
}
