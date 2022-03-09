## Ignore this file, just testing socker programming on the server side
import socket

def main():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind(('localhost', 2222))
        s.listen()
        conn, addr = s.accept()
        with conn:
            print(f"Connection from {addr}")
            while True:
                data = conn.recv(1024)
                if not data:
                    break
                print(f'Received : {data} from client')
                conn.sendall(str.encode('Received'))
             
if __name__ == "__main__":
    while True:
        main()