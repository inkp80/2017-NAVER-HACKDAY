cmake_minimum_required(VERSION 3.8)
project(VideoStabilizer)

set(CMAKE_CXX_STANDARD 11)
find_package( OpenCV REQUIRED )

set(SOURCE_FILES VideoMask.cpp)
add_executable(VideoStabilizer ${SOURCE_FILES})
target_link_libraries(VideoStabilizer ${OpenCV_LIBS})