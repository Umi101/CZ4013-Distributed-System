import sys

class Console():

    def prompt_int(self,question,**kwargs):
        while True:
            try:
                data = int(input(question))
                if kwargs:
                    assert kwargs.get('minimum',sys.maxsize) <= data <= kwargs.get('maximum',-sys.maxsize)
            except ValueError:
                continue
            except AssertionError:
                print('Please enter a value between the specified range')
                continue
            else:
                return data

    def prompt_float(self,question,**kwargs):
        while True:
            try:
                data = float(input(question))
                if kwargs:
                    assert kwargs.get('minimum',float('inf')) <= data <= kwargs.get('maximum',float('-inf'))
            except ValueError:
                continue
            except AssertionError:
                print('Please enter a value between the specified range')
                continue
            else:
                return data
            
    def prompt_string(self,question):
        while True:
            try:
                data = str(input(question))
            except ValueError:
                continue
            else:
                return data


 