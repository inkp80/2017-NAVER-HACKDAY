# 2017 네이버 캠퍼스 핵데이

## Time snap 2.0
- 동영상 플레이어
- Video Crop
- Cinemagraph (Video to GIF)

### 손떨림 보정
- OpenCV 
- ImageStabilizer : ORB | BRISK + Homograph (Thanks to 김일호 / 추교정)
- VideoStabilizer : goodFeatureToTrack & calcOpticalFlowPyrLK + Trajectory <br>(Nghia Ho http://nghiaho.com/, chen jia)

### 동영상 플레이어
- SurfaceView, VideoView, MediaPlayer, MediaController

### VideoCrop
- ImageCrop Library 참조
- https://github.com/ArthurHub/Android-Image-Cropper
- https://github.com/edmodo/cropper

### Decode & Encode
- MediaCodec & MediaMuxer
