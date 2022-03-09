## Ignore this file, just testing socker programming on the server side
import socket

def main():
    s =  socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    s.bind(('localhost', 2222))

    while True:
        data, address = s.recvfrom(1024)
        print(f'Received : {data} from client address : {address}')
        s.sendto(str.encode('Received'),address)
             
if __name__ == "__main__":
    while True:
        main()