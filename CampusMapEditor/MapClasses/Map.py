from MapClasses.GrassZone import GrassZone
from MapClasses.Road import Road
from MapClasses.Structure import Structure


class Map:
    def __init__(self):
        self.__structureList = []
        self.__grassList = []
        self.__roadList = []

    def add_structure(self):
        self.__structureList.append(Structure())

    def get_grass_list(self):
        return self.__grassList

    def get_road_list(self):
        return self.__roadList

    def delete_last_structure(self):
        self.__structureList.pop()

    def get_structure_list(self):
        return self.__structureList

    def last_structure(self):
        return self.__structureList[len(self.__structureList) - 1]

    def encode(self):
        data = ""

        for struct in self.get_structure_list():
            data += str(struct.get_type()) + ">>"
            data += str(struct.get_location()) + ">>"
            data += str(struct.get_name()) + ">>"
            for i in range(len(struct.vectorsX)):
                data += str(struct.vectorsX[i])+"|"+str(struct.vectorsY[i])+">"
            data += "@"
        data +="<<>>@"
        for grass in self.__grassList:
            for i in range(len(grass.vectorsX)):
                data += str(grass.vectorsX[i])+"|"+str(grass.vectorsY[i])+">"
            data += "@"
        data += "<<>>@"
        for road in self.__roadList:
            data += str(road.get_type()) + ">>"
            for i in range(len(road.vectorX)):
                data += str(road.vectorX[i])+"|"+str(road.vectorY[i])+">"
            data += "@"
        return data

    def decode(self, data):
        data = data.split("<<>>@")
        dataStructure = data[0]
        dataStructure = dataStructure.split('@')
        for i in range(len(dataStructure)-1):
            param = dataStructure[i].split(">>")

            self.get_structure_list().append(Structure())
            self.last_structure().set_type(int(param[0]))
            self.last_structure().set_location(param[1])
            self.last_structure().set_name(param[2])

            vectors = param[3].split(">")
            for j in range(len(vectors)-1):
                vector = vectors[j].split("|")
                self.last_structure().add_vector(int(vector[0]), int(vector[1]))

        dataGrass = data[1].split("@")
        for i in range(len(dataGrass)-1):
            param = dataGrass[i].split(">>")

            self.get_grass_list().append(GrassZone())

            vectors = param[0].split(">")
            for j in range(len(vectors)-1):
                vector = vectors[j].split("|")
                self.get_grass_list()[len(self.get_grass_list()) - 1].add_vector(int(vector[0]), int(vector[1]))

        dataRoad = data[2].split('@')
        for i in range(len(dataRoad) - 1):
            param = dataRoad[i].split(">>")

            self.get_road_list().append(Road())
            self.__roadList[len(self.__roadList) - 1].set_type(int(param[0]))

            vectors = param[1].split(">")
            for j in range(len(vectors) - 1):
                vector = vectors[j].split("|")
                self.__roadList[len(self.__roadList) - 1].add_vector(int(vector[0]), int(vector[1]))