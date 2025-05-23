class Data:
    def __init__(self):
        self.df = None
        self.target = 'WF_Power'
        self.direccion = 'wd'
        self.time = 'TIME'

    def get_data(self):
        return self.df

    def get_target(self):
        return self.target

    def get_direccion(self):
        return self.direccion

    def get_time(self):
        return self.time
    
    def set_data(self, data):
        self.df = data

    def set_target(self, target):
        self.target = target

    def set_direccion(self, direccion):
        self.direccion = direccion

    def set_time(self, time):
        self.time = time