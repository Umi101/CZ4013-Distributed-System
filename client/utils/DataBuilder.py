from utils.marshall import *
import struct

class DataBuilder():
    def __init__(self):
        self.buffer = [] # We will be sending this buffer to the server

    def set_string(self,key:str,value:str):
        self.buffer.append(self.str_to_byte(key))
        self.buffer.append(self.str_to_byte(value))
        return self

    def set_int(self,key:str,value:int):
        self.buffer.append(self.str_to_byte(key))
        self.buffer.append(self.int_to_byte(value))
        return self

    def set_float(self,key,value):
        self.buffer.append(self.str_to_byte(key))
        self.buffer.append(self.float_to_byte(value))
        return self

    def int_to_byte(self, i:int) -> bytes:
        return i.to_bytes(2, 'big')

    def str_to_byte(self, s:str) -> bytes:
        return str.encode(s)

    def float_to_byte(self, f:float) -> bytes:
        return bytes(bytearray(struct.pack("f", f)))