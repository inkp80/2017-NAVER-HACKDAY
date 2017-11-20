cmake_minimum_required(VERSION 3.8)
project(cpp)

set(CMAKE_CXX_STANDARD 11)
find_package( OpenCV REQUIRED )

set(SOURCE_FILES VideoStabilizer2.cpp)
#main.cpp VideoStabilizer.cpp
add_executable(cpp ${SOURCE_FILES})

target_link_libraries(cpp ${OpenCV_LIBS})
