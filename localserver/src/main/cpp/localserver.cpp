#include <jni.h>
#include <string>
#include <iostream>
#include <memory>
#include "server/server.h"
#include "common/common.h"

// 初始化HttpServer静态类成员
mg_serve_http_opts HttpServer::s_server_option;
std::string HttpServer::s_web_dir = "./web";
std::unordered_map<std::string, ReqHandler> HttpServer::s_handler_map;
std::unordered_set<mg_connection *> HttpServer::s_websocket_session_set;

bool handle_fun1(std::string url, std::string body, mg_connection *c, OnRspCallback rsp_callback)
{
    // do sth
    std::cout << "handle fun1" << std::endl;
    std::cout << "url: " << url << std::endl;
    std::cout << "body: " << body << std::endl;

    rsp_callback(c, "rsp1");

    return true;
}

bool handle_fun2(std::string url, std::string body, mg_connection *c, OnRspCallback rsp_callback)
{
    // do sth
    std::cout << "handle fun2" << std::endl;
    std::cout << "url: " << url << std::endl;
    std::cout << "body: " << body << std::endl;

    rsp_callback(c, "rsp2");

    return true;
}

bool handle_fun3(std::string url, std::string body, mg_connection *c, OnRspCallback rsp_callback)
{
    // do sth
    std::cout << "handle fun2" << std::endl;
    std::cout << "url: " << url << std::endl;
    std::cout << "body: " << body << std::endl;

    rsp_callback(c, "rsp2");

    return true;
}


extern "C" JNIEXPORT jstring JNICALL
Java_cn_ololee_localserver_NativeLib_startServer(
        JNIEnv* env,
        jclass /* this */) {
    LOGE("===========================start server===========================");
    std::string hello = "Hello from C++";
    std::string port = "7999";
    auto http_server = std::shared_ptr<HttpServer>(new HttpServer);
    LOGE("===========================before init===========================");
    http_server->Init(port);
    LOGE("===========================after init===========================");
    // add handler

    http_server->AddHandler("/api/fun1", handle_fun1);
    http_server->AddHandler("/api/fun2", handle_fun2);
    http_server->AddHandler("/api/fun3", handle_fun1);
    LOGE("===========================after add handler===========================");
    http_server->Start();
    return env->NewStringUTF(hello.c_str());
}

