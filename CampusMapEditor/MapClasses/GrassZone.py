class GrassZone:
    __color = "#C4EFC7"

    def __init__(self):
        self.vectorsX = []
        self.vectorsY = []

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

    def get_color(self):
        return self.__color