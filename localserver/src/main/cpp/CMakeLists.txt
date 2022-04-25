
cmake_minimum_required(VERSION 3.18.1)
project("localserver" CXX C)

add_library(localserver
             SHARED
             common/mongoose.c
             server/server.cpp
             localserver.cpp )



find_library(log-lib
              log )


target_link_libraries(localserver
                       ${log-lib} )