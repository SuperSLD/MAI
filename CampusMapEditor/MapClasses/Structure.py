from math import hypot


class Structure:
    __colors = ["#0480B7", "#C4C4C4", "#FFDA8C", "#0094FF"]

    def __init__(self):
        self.vectorsX = []
        self.vectorsY = []
        self.__type = 0
        self.__struct_location = ""
        self.__struct_name = ""

    def set_type(self, type):
        self.__type = type

    def add_vector(self, x, y):
        self.vectorsX.append(x)
        self.vectorsY.append(y)

    def get_points(self, cameraX, cameraY, zoom):
        points = []
        for i in range(len(self.vectorsY)):
            points.append(self.vectorsX[i]*zoom + cameraX)
            points.append(self.vectorsY[i]*zoom + cameraY)
        return points

    def delete_last_vector(self):
        self.vectorsX.pop()
        self.vectorsY.pop()

    def get_type(self):
        return self.__type

    def get_location(self):
        return self.__struct_location

    def get_name(self):
        return self.__struct_name

    def set_name(self, name):
        self.__struct_name = name

    def set_location(self, data):
        self.__struct_location = data

    def get_color(self):
        return self.__colors[self.__type]

    def pointIn(self, x, y):
        maxX = -100000
        maxY = -100000
        minX = 100000
        minY = 100000

        for i in range(len(self.vectorsX)):
            if self.vectorsX[i] > maxX:
                maxX = self.vectorsX[i]
            if self.vectorsX[i] < minX:
                minX = self.vectorsX[i]
            if self.vectorsY[i] > maxY:
                maxY = self.vectorsY[i]
            if self.vectorsY[i] < minY:
                minY = self.vectorsY[i]

        x -= minX
        y -= minY
        mX = int((maxX - minX)/2)
        mY = int((maxY - minY)/2)

        p = hypot(mX - x, mY - y)

        return [p, [maxX, maxY, maxX, minY, minX, minY, minX, maxY]]