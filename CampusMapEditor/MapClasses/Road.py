class Road:
    __colors = ["#DBDBDB", "#FFFFFF", "#FFFFFF", "#FFF4C4"]
    __size = [4, 10, 20, 25]
    __dash = [(4, 2), (), (), ()]

    def __init__(self):
        self.__type = 0
        self.vectorX = []
        self.vectorY = []

    def set_type(self, type):
        self.__type = type

    def get_type(self):
        return self.__type

    def add_vector(self, x, y):
        self.vectorX.append(x)
        self.vectorY.append(y)

    def get_points(self, cameraX, cameraY, zoom):
        points = []
        for i in range(len(self.vectorY)):
            points.append(self.vectorX[i]*zoom + cameraX)
            points.append(self.vectorY[i]*zoom + cameraY)
        return points

    def delete_last_vector(self):
        self.vectorX.pop()
        self.vectorY.pop()

    def get_color(self):
        return self.__colors[self.__type]

    def get_size(self):
        return self.__size[self.__type]

    def get_dash(self):
        return self.__dash[self.__type]
