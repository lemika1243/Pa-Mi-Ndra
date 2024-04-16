#include <stdlib.h>
int main(int argc, char const *argv[])
{
    system("ipconfig | findstr /i ipv4 > settings/ip.pa");
    system("ip a | grep 'scope g' > settings/ip.pa");
    system("java socket.Server");
    return 0;
}