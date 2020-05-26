import tkinter as tk
import os
from MapClasses.GrassZone import GrassZone
from MapClasses.Road import Road
from MapClasses.Map import Map


size = 600
root = tk.Tk()
canvas = tk.Canvas(root, width=size * 2, height=size)
canvas.pack()
canvas.pack(fill="both", expand=True)
root.title("Editor")

map = Map()
file = open("campusMap.campus", encoding="utf-8")
map.decode(file.read())

img = tk.PhotoImage(file=os.getcwd() + '\images\yandexMap.png')

createStr = False
moveMap = False
drawYandexMap = False
grassCreate = False
roadCreate = False

minStruct = None
minBorder = []

startX = 0
startY = 0
cameraX = 658
cameraY = 247

zoom = 0.2

def create_map_object(event):
    global createStr
    global grassCreate
    global roadCreate
    global zoom
    global minStruct
    global minBorder
    global drawYandexMap

    print("keycode = " + str(event.keycode) + " / " + str(createStr))
    if not moveMap:
        if event.keycode == 32:
            if not createStr:
                map.add_structure()
                root.title("Editor / structure create / 0")
                print("structure create / keycode = " + str(event.keycode))
                createStr = True
            else:
                root.title("Editor")
                print("end create points")
                if len(map.last_structure().get_points(cameraX, cameraY, zoom)) == 0:
                    map.delete_last_structure()
                createStr = False
        elif event.keycode == 65:
            if createStr:
                map.last_structure().set_type(map.last_structure().get_type()-1)
                root.title("Editor / structure create / " + str(map.last_structure().get_type()))
            if roadCreate:
                map.get_road_list()[len(map.get_road_list()) - 1].set_type(map.get_road_list()[len(map.get_road_list()) - 1].get_type()-1)
                root.title("Editor / structure create / " + str(map.get_road_list()[len(map.get_road_list()) - 1].get_type()))
        elif event.keycode == 83:
            if createStr:
                map.last_structure().set_type(map.last_structure().get_type()+1)
                root.title("Editor / structure create / " + str(map.last_structure().get_type()))
            if roadCreate:
                map.get_road_list()[len(map.get_road_list()) - 1].set_type(map.get_road_list()[len(map.get_road_list()) - 1].get_type()+1)
                root.title("Editor / structure create / " + str(map.get_road_list()[len(map.get_road_list()) - 1].get_type()))
        if event.keycode == 90:
            if createStr:
                map.last_structure().delete_last_vector()
            if grassCreate:
                map.get_grass_list()[len(map.get_grass_list()) - 1].delete_last_vector()
            if roadCreate:
                map.get_road_list()[len(map.get_road_list()) - 1].delete_last_vector()

        if event.keycode == 71:
            if not grassCreate:
                grassCreate = True
                map.get_grass_list().append(GrassZone())
                root.title("Editor / grass create")
                print("grass create")
            else:
                root.title("Editor")
                print("end create points")
                if len(map.get_grass_list()[len(map.get_grass_list()) - 1].get_points(cameraX, cameraY, zoom)) == 0:
                    map.get_grass_list().pop()
                grassCreate = False
        if event.keycode == 82:
            if not roadCreate:
                roadCreate = True
                map.get_road_list().append(Road())
                root.title("Editor / road create")
                print("road create")
            else:
                root.title("Editor")
                print("end create points")
                if len(map.get_road_list()[len(map.get_road_list()) - 1].get_points(cameraX, cameraY, zoom)) == 0:
                    map.get_road_list().pop()
                roadCreate = False

    if event.keycode == 8:
        drawYandexMap = not drawYandexMap
    if event.keycode == 38:
        zoom -= 0.1
    if event.keycode == 40:
        zoom += 0.1
    if event.keycode == 46 and minStruct != None:
        map.get_structure_list().remove(minStruct)
        minStruct = None
    if event.keycode == 13:
        data = map.encode()
        file = open("campusMap.campus", "w", encoding="utf-8")
        print(data)
        file.write(data)

    draw_map()


def draw_map():
    canvas.delete("all")
    if drawYandexMap:
        canvas.create_image(cameraX, cameraY, image=img)
    for grass in map.get_grass_list():
        if len(grass.get_points(cameraX, cameraY, zoom)) != 0:
            if drawYandexMap:
                canvas.create_polygon(grass.get_points(cameraX, cameraY, zoom), fill="", outline="#00A889")
            else:
                canvas.create_polygon(grass.get_points(cameraX, cameraY, zoom), fill=grass.get_color())
    for road in map.get_road_list():
        if len(road.get_points(cameraX, cameraY, zoom)) >= 4:
            if drawYandexMap:
                canvas.create_line(road.get_points(cameraX, cameraY, zoom), fill="#9B9B9B", width=road.get_size()*zoom, dash=road.get_dash())
            else:
                canvas.create_line(road.get_points(cameraX, cameraY, zoom), fill=road.get_color(), width=road.get_size()*zoom, dash=road.get_dash())
    for struct in map.get_structure_list():
        if len(struct.get_points(cameraX, cameraY, zoom)) != 0:
            if drawYandexMap:
                canvas.create_polygon(struct.get_points(cameraX, cameraY, zoom), fill="", outline=struct.get_color())
            else:
                canvas.create_polygon(struct.get_points(cameraX, cameraY, zoom), fill=struct.get_color())

    if minStruct != None:
        xy = [0 for _ in range(8)]
        for i in range(len(minBorder)):
            xy[i] = (minBorder[i]*zoom + cameraX) if i % 2 == 0 else (minBorder[i]*zoom + cameraY)
        canvas.create_polygon(xy, fill='', outline="red")


def draw_point(event):
    global createStr
    global minStruct
    if createStr and not moveMap:
        structure = map.last_structure()
        structure.add_vector(int((event.x - cameraX)/zoom), int((event.y - cameraY)/zoom))
        draw_map()

        canvas.create_oval(event.x, event.y, event.x, event.y)
    if grassCreate and not moveMap:
        grass = map.get_grass_list()[len(map.get_grass_list()) - 1]
        grass.add_vector(int((event.x - cameraX)/zoom), int((event.y - cameraY)/zoom))
        draw_map()

        canvas.create_oval(event.x, event.y, event.x, event.y)
    if roadCreate and not moveMap:
        road = map.get_road_list()[len(map.get_road_list()) - 1]
        road.add_vector(int((event.x - cameraX)/zoom), int((event.y - cameraY)/zoom))
        draw_map()

        canvas.create_oval(event.x, event.y, event.x, event.y)

    if not createStr and not grassCreate and not createStr:
        global minBorder
        global minStruct
        min = 100000
        minResult = [0, []]
        for structure in map.get_structure_list():
            result = structure.pointIn(int((event.x - cameraX)/zoom), int((event.y - cameraY)/zoom))
            if min > result[0]:
                minResult = result
                minStruct = structure
                min = minResult[0]
        print(minResult[1])
        minBorder = minResult[1]

        draw_map()

def start_move(event):
    global moveMap
    global startX
    global startY
    moveMap = not moveMap
    if moveMap:
        startX = event.x - cameraX
        startY = event.y - cameraY


def move(event):
    global cameraX
    global cameraY
    if moveMap:
        cameraX = event.x - startX
        cameraY = event.y - startY
        print("x: " + str(cameraX) + " / y: " + str(cameraY))
        draw_map()


root.bind("<Key>", create_map_object)
root.bind("<Button-1>", draw_point)
root.bind("<Button-3>", start_move)
root.bind("<Motion>", move)
draw_map()
root.mainloop()
