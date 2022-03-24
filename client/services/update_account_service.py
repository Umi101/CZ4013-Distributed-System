from typing import Type
from utils.Console import Console
from utils.Client import Client
from utils.DataBuilder import DataBuilder

def update_account(
    console: Type[Console],
    client: Type[Client]
    ) -> None:
    
    service_id = 3
    message_id = client.get_message_id()
    next_choice = console.prompt_int('Enter 1 to withdraw / Enter 2 to deposit: ', minimum = 1, maximum = 2)
    name = console.prompt_string('Enter your name : ')
    acc_no = console.prompt_int('Enter your account number : ')
    password = console.prompt_int('Enter your 4 digit pin number : ')
    while len(str(password)) != 4:
        password = console.prompt_int('Enter your 4 digit pin number : ')
    amount = console.prompt_float('Enter amount : ', minimum = 0.01, maximum = float('inf'))
    while amount < 0:
        amount = console.prompt_float('Enter amount : ', minimum=0.01, maximum=float('inf'))
    # if next_choice == 1:
    #     amount = -amount
    
    # Construct byte array
    builder = DataBuilder()
    builder.set_two_byte('service_id',service_id).\
        set_int('message_id',message_id).\
        set_int('withdraw_choice',next_choice).\
        set_string('name',name). \
        set_int('acc_no', acc_no). \
        set_int('password',password).\
        set_float('amount',amount)

    print(builder.buffer)
    print(builder.create())

    # Send data
    client.send(builder.create())

    # Receive response
    response = client.receive()
    print(response.decode('ascii'))