'''
Created on Apr 10, 2018
@author: Omri
'''
import os
import io
import random




# def GetJavaOutput(filepath,filename):
#     command = "java -classpath "+PATH_TO_MINIMAL_MODEL+" Rules/MinimalModel "+filepath+"/"+filename
#     return os.popen(command).read()

path = 'C:/Users/mazma/eclipse-workspace/Modular-Construction-of-Minimal-Models/'


def RandomGraphMaker(M):
    filename = "graph_"+str(M)
    filePath = os.path.join(path, filename)
    # myFile = io.FileIO(filePath, 'r', encoding="utf-8")
    myFile = open(filePath+".txt", 'w', encoding="utf-8")
    line = ""
    for i in range(0, M):
        line = str(i) + " "
        k = random.randint(2, M)
        for j in range(0, k):
            n = random.randint(0, j)
            if(n != i):
                line += str(j) + " "
        myFile.write(line + "\n")
    return myFile




fmyFile = RandomGraphMaker(13)
fmyFile.close()


