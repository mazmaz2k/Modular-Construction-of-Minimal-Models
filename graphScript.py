'''
Created on Apr 10, 2018
@author: Omri
'''
import os
import io
import random

path = "C:/Users\mazma\eclipse-workspace\Modular-Construction-of-Minimal-Models\\bin"
pathToFile = "C:/Users\mazma\eclipse-workspace\Modular-Construction-of-Minimal-Models\\testFiles\graphTestFiles"

def GetJavaOutput(filepath,filename):
    command = "java -classpath "+path+" Test/readGraphFromFileNewAlgorithm " +filepath+"/"+filename
    return os.popen(command).read()



def RandomGraphMaker(M):
    filename = "graph_"+str(M)
    filePath = os.path.join(pathToFile, filename)
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


#
# s = GetJavaOutput(pathToFile, filename)
#
# print(s)
#fmyFile = RandomGraphMaker(13)
#fmyFile.close()


def X():
    outList=[]
    varList=[200]
    for i in varList:
        RandomGraphMaker(i)
        filename="graph_"+str(i)+".txt"
        out = GetJavaOutput(pathToFile,filename)
       #os.remove(os.path.join(pathToFile, filename))
        print(str(i)+" variables-----" + out)


X()
