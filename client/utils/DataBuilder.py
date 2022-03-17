from utils.marshall import *
import struct

class DataBuilder():
    def __init__(self):
        self.buffer = [] # We will be sending this buffer to the server

    def set_two_byte(self,key:str,value:str):
        self.buffer.append(self.int_to_byte(value))
        return self

    def set_string(self,key:str,value:str):
        self.buffer.append(self.str_to_byte(key))
        self.buffer.append(self.integer_to_byte(len(value)))
        self.buffer.append(self.str_to_byte(value))
        return self

    def set_int(self,key:str,value:int):
        self.buffer.append(self.str_to_byte(key))
        self.buffer.append(self.integer_to_byte(value))
        return self

    def set_float(self,key,value):
        self.buffer.append(self.str_to_byte(key))
        self.buffer.append(self.float_to_byte(value))
        return self

    def integer_to_byte(self, i:int) -> bytes:
        return i.to_bytes(4, byteorder = 'big')

    def int_to_byte(self, i:int) -> bytes:
        return i.to_bytes(2, 'big')

    def str_to_byte(self, s:str) -> bytes:
        return str.encode(s)

    def float_to_byte(self, f:float) -> bytes:
        # return bytes(bytearray(struct.pack("f", f)))
        return bytes(bytearray(struct.pack(">d", f)))  # convert to big endian

    def create(self):
        return b''.join(self.buffer)