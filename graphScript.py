'''
Created on Apr 10, 2018
@author: Omri
'''
import os
import io
import random

path = "C:/Users\mazma\eclipse-workspace\Modular-Construction-of-Minimal-Models\\bin"
pathToFile = "C:/Users\mazma\eclipse-workspace\Modular-Construction-of-Minimal-Models\\testFiles\graphTestFiles"
path1 = "C:/Users\mazma\eclipse-workspace\Modular-Construction-of-Minimal-Models\\testFiles"

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
            if n != i:
                line += str(n) + " "
        myFile.write(line + "\n")
    return myFile


#
# s = GetJavaOutput(pathToFile, filename)
#
# print(s)
#fmyFile = RandomGraphMaker(13)
#fmyFile.close()


# def X():
#     outList=[]
#     varList=[130]
#     for i in varList:
#         RandomGraphMaker(i)
#         filename="graph_"+str(i)+".txt"
#         out = GetJavaOutput(pathToFile,filename)
#        #os.remove(os.path.join(pathToFile, filename))
#         print(str(i)+" variables-----" + out)
#
#
# X()


def RandomPositiveCNF(L, M, K):
    filename = "cnf_" + str(L) + "_" + str(M)
    filepath = os.path.join(path1, filename)
    # myFile = io.FileIO(filepath, "w")
    # myFile.write(str(L) + "\n")
    # for i in range(0, L):
    #     line = ""
    #     allNegative = True
    #     for j in range(0, K):
    #         var = random.randint(1, M)
    #         if (j != K - 1 or not allNegative):
    #             if (random.getrandbits(1)):
    #                 var = var * -1
    #         if (var > 0):
    #             allNegative = False
    #         line += str(var) + " "
    #     line += "0"
    #     myFile.write(line + "\n")
    return myFile



def printSources():
    K = 3
    M = 50
    L = 100
    RandomPositiveCNF(L, M, K)
    #filename = "cnf_" + str(L) + "_" + str(M)
    #output = GetJavaOutput(path, filename)
    #print(output)


#for i in range(0, 10):
printSources()

