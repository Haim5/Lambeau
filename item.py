class Item:
    def __init__(self, x, y, z, width=1, depth=1, height=1, weight=0, is_stackable=True, can_flip=True, group=0):
        self.__x = x
        self.__y = y
        self.__z = z
        self.__width = width
        self.__depth = depth
        self.__height = height
        self.__weight = weight
        self.__is_stackable = is_stackable
        self.__can_flip = can_flip
        self.__group = group
        self.__orientations = set()
        if can_flip:
            self.__orientations.update(set((width, depth, height), (width, height, depth), (height, width, depth),
                                            (depth, width, height), (depth, height, width), (height, depth, width)))
        else:
            self.__orientations.update(set((width, depth, height), (depth, width, height)))

    def get_weight(self):
        return self.__weight
    
    def get_width(self):
        return self.__width
    
    def get_depth(self):
        return self.__depth
    
    def get_height(self):
        return self.__height
    
    def set_x(self, x):
        self.__x = x

    def set_y(self, y):
        self.__y = y

    def set_z(self, z):
        self.__z = z 

    def get_x(self):
        return self.__x
    
    def get_y(self):
        return self.__y

    def get_z(self):
        return self.__z