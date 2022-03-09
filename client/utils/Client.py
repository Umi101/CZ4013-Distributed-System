import socket

class Client():
    def __init__(self,server_port,server_address):
        self.server_port = server_port
        self.server_address = server_address
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        self.message_id = 0
    
    def get_server_address(self):
        return self.server_address

    def get_server_port(self):
        return self.server_port
    
    def get_message_id(self):
        tmp = self.message_id
        self.message_id += 1        
        return tmp

    def send(self,byte_data):
        self.socket.sendto(byte_data,(self.server_address, self.server_port))

    def receive(self):
        data = self.socket.recv(2048)
        return data
          

