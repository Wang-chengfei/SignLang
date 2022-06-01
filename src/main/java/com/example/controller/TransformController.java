package com.example.controller;

import com.baidu.aip.speech.AipSpeech;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;

import org.json.JSONObject;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wcf
 * @since 2022-05-23
 */
@RestController
@RequestMapping("/transform")
public class TransformController {

    // 百度语音识别
    public static final String APP_ID = "26316015";
    public static final String API_KEY = "HKTKhXLx2W4gChhRS2GkCIc4";
    public static final String SECRET_KEY = "MTQFd4zgqCBHzUsGLjw58topzVNsbRdn";

    /**
     * @Description TODO
     * @return
     */
    @RequestMapping(value = "/speechRecognition", method = RequestMethod.POST)
    public Object speechReco(HttpServletRequest request) {
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        try {

//            InputStream is = file.getInputStream();
//            FileOutputStream fos = new FileOutputStream("b.wav");
//            byte[] b = new byte[1024];
//            while ((is.read(b)) != -1) {
//                fos.write(b);// 写入数据
//            }
//            is.close();
//            fos.close();// 保存数据

//            Process process = new ProcessBuilder("ffmpeg", "-y", "-i", "b.mp3", "b.wav").start();
//            byte[] pcmBytes = mp3Convertpcm(file.getInputStream());
            byte[] wavBytes = IOUtils.toByteArray(file.getInputStream());
            JSONObject resultJson = speechBdApi(wavBytes);
            System.out.println(resultJson.toString());
            if (null != resultJson && resultJson.getInt("err_no") == 0) {
                return resultJson.getJSONArray("result").get(0).toString().split("，")[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

//    /**
//     * @Description MP3转换pcm
//     * @param mp3Stream
//     *            原始文件流
//     * @return 转换后的二进制
//     * @throws Exception
//     */
//    public byte[] mp3Convertpcm(InputStream mp3Stream) throws Exception {
//        // 原MP3文件转AudioInputStream
//        BufferedInputStream zipTest = new BufferedInputStream(mp3Stream);
//        //重新包装一层，不然会报错。
////        AudioInputStream mp3audioStream = AudioSystem.getAudioInputStream(zipTest);
//        // 将AudioInputStream MP3文件 转换为PCM AudioInputStream
////        AudioInputStream pcmaudioStream = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED,
////                mp3audioStream);
//        byte[] pcmBytes = IOUtils.toByteArray(zipTest);
////        mp3audioStream.close();
////        mp3audioStream.close();
//        return pcmBytes;
//    }

    /**
     * @Description 调用百度语音识别API
     * @param pcmBytes
     * @return
     */
    public static JSONObject speechBdApi(byte[] pcmBytes) {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 调用接口
        JSONObject res = client.asr(pcmBytes, "wav", 16000, null);
        return res;
    }

}

