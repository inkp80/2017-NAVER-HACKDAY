# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.8

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /Applications/CLion.app/Contents/bin/cmake/bin/cmake

# The command to remove a file.
RM = /Applications/CLion.app/Contents/bin/cmake/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/macbook/github/ImageStabilizer/cpp

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/macbook/github/ImageStabilizer/cpp/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/cpp.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/cpp.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/cpp.dir/flags.make

CMakeFiles/cpp.dir/VideoStabilizer2.cpp.o: CMakeFiles/cpp.dir/flags.make
CMakeFiles/cpp.dir/VideoStabilizer2.cpp.o: ../VideoStabilizer2.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/macbook/github/ImageStabilizer/cpp/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/cpp.dir/VideoStabilizer2.cpp.o"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/cpp.dir/VideoStabilizer2.cpp.o -c /Users/macbook/github/ImageStabilizer/cpp/VideoStabilizer2.cpp

CMakeFiles/cpp.dir/VideoStabilizer2.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/cpp.dir/VideoStabilizer2.cpp.i"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/macbook/github/ImageStabilizer/cpp/VideoStabilizer2.cpp > CMakeFiles/cpp.dir/VideoStabilizer2.cpp.i

CMakeFiles/cpp.dir/VideoStabilizer2.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/cpp.dir/VideoStabilizer2.cpp.s"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/macbook/github/ImageStabilizer/cpp/VideoStabilizer2.cpp -o CMakeFiles/cpp.dir/VideoStabilizer2.cpp.s

CMakeFiles/cpp.dir/VideoStabilizer2.cpp.o.requires:

.PHONY : CMakeFiles/cpp.dir/VideoStabilizer2.cpp.o.requires

CMakeFiles/cpp.dir/VideoStabilizer2.cpp.o.provides: CMakeFiles/cpp.dir/VideoStabilizer2.cpp.o.requires
	$(MAKE) -f CMakeFiles/cpp.dir/build.make CMakeFiles/cpp.dir/VideoStabilizer2.cpp.o.provides.build
.PHONY : CMakeFiles/cpp.dir/VideoStabilizer2.cpp.o.provides

CMakeFiles/cpp.dir/VideoStabilizer2.cpp.o.provides.build: CMakeFiles/cpp.dir/VideoStabilizer2.cpp.o


# Object files for target cpp
cpp_OBJECTS = \
"CMakeFiles/cpp.dir/VideoStabilizer2.cpp.o"

# External object files for target cpp
cpp_EXTERNAL_OBJECTS =

cpp: CMakeFiles/cpp.dir/VideoStabilizer2.cpp.o
cpp: CMakeFiles/cpp.dir/build.make
cpp: /usr/local/lib/libopencv_dnn.3.3.1.dylib
cpp: /usr/local/lib/libopencv_ml.3.3.1.dylib
cpp: /usr/local/lib/libopencv_objdetect.3.3.1.dylib
cpp: /usr/local/lib/libopencv_shape.3.3.1.dylib
cpp: /usr/local/lib/libopencv_stitching.3.3.1.dylib
cpp: /usr/local/lib/libopencv_superres.3.3.1.dylib
cpp: /usr/local/lib/libopencv_videostab.3.3.1.dylib
cpp: /usr/local/lib/libopencv_calib3d.3.3.1.dylib
cpp: /usr/local/lib/libopencv_features2d.3.3.1.dylib
cpp: /usr/local/lib/libopencv_flann.3.3.1.dylib
cpp: /usr/local/lib/libopencv_highgui.3.3.1.dylib
cpp: /usr/local/lib/libopencv_photo.3.3.1.dylib
cpp: /usr/local/lib/libopencv_video.3.3.1.dylib
cpp: /usr/local/lib/libopencv_videoio.3.3.1.dylib
cpp: /usr/local/lib/libopencv_imgcodecs.3.3.1.dylib
cpp: /usr/local/lib/libopencv_imgproc.3.3.1.dylib
cpp: /usr/local/lib/libopencv_core.3.3.1.dylib
cpp: CMakeFiles/cpp.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/macbook/github/ImageStabilizer/cpp/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable cpp"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/cpp.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/cpp.dir/build: cpp

.PHONY : CMakeFiles/cpp.dir/build

CMakeFiles/cpp.dir/requires: CMakeFiles/cpp.dir/VideoStabilizer2.cpp.o.requires

.PHONY : CMakeFiles/cpp.dir/requires

CMakeFiles/cpp.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/cpp.dir/cmake_clean.cmake
.PHONY : CMakeFiles/cpp.dir/clean

CMakeFiles/cpp.dir/depend:
	cd /Users/macbook/github/ImageStabilizer/cpp/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/macbook/github/ImageStabilizer/cpp /Users/macbook/github/ImageStabilizer/cpp /Users/macbook/github/ImageStabilizer/cpp/cmake-build-debug /Users/macbook/github/ImageStabilizer/cpp/cmake-build-debug /Users/macbook/github/ImageStabilizer/cpp/cmake-build-debug/CMakeFiles/cpp.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/cpp.dir/depend

