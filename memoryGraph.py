'''
Created on Apr 10, 2018
@author: adi tayri
'''
import os
import io
import random

import plotly
import plotly.plotly as py
import plotly.graph_objs as go
import subprocess
plotly.tools.set_credentials_file(username='adita', api_key='T5ZzZ3SsaTXrHcAVwbJU')
from subprocess import STDOUT,PIPE



PATH_TO_MINIMAL_MODEL="/home/rachel/Desktop/Modular-Construction-of-Minimal-Models/bin"
def GetJavaOutput(filepath,filename):
    command = "java -classpath "+PATH_TO_MINIMAL_MODEL+" Rules/MinimalModel "+filepath+"/"+filename
    return os.popen(command).read()
# 
# s= GetMemoryFromJava(".//CnfFile.txt", "").split(",")
# print(s[0])
path="/home/rachel/Desktop/Modular-Construction-of-Minimal-Models/testFiles"
# def RandomPositiveCNF(L , M , K):
#     directory="/"+str(M)+"variables"
#     filename="cnf_"+str(L)+"_"+str(M)
#     filepath=os.path.join(path+directory,filename)
#     myFile=io.FileIO(filepath, "w") 
#     myFile.write(str(L)+"\n")
#     for i in range(0,L):  
#         line=""
#         allNegative=True
#         for j in range(0, K):
#             var=random.randint(1,M)
#             if(j!=K-1 or not allNegative):
#                 if(random.getrandbits(1)):
#                     var=var*-1    
#             if(var>0):
#                 allNegative=False            
#             line += str(var)+" "
#         line+="0" 
#         myFile.write(line+"\n")

def RandomPositiveCNF(L , M , K):
    filename="cnf_"+str(L)+"_"+str(M)
    filepath=os.path.join(path,filename)
    myFile=io.FileIO(filepath, "w") 
    myFile.write(str(L)+"\n")
    for i in range(0,L):  
        line=""
        allNegative=True
        for j in range(0, K):
            var=random.randint(1,M)
            if(j!=K-1 or not allNegative):
                if(random.getrandbits(1)):
                    var=var*-1    
            if(var>0):
                allNegative=False            
            line += str(var)+" "
        line+="0" 
        myFile.write(line+"\n")
    return myFile
    
def frange(start, stop, step):
    i = start
    while i < stop:
        yield i
        i += step

def FindAVG(arr):
    s=0.0
    size=len(arr)
    if size == 0:
        return
    for i in arr:
        fi=float(i)
        s=s+fi
    avg=s/size
    return avg   
     
        
def FindMedian(arr):
    median=0.0
    size = len(arr)  
    if size == 0:
        return
    arr.sort(cmp=None, key=None, reverse=False) 
    if size%2 == 0:
        num1=float(arr[(size-2)/2])
        num2=float(arr[size/2])
        median=(num1+num2)/2
    else:
        middle=(size-1)/2
        median = arr[middle] 
        
    return median   
    
def testEverage(): 
    K = 3 #K-SAT
    variableList=[20,40,50]
    data=[]
    for M in variableList:
        directory="/"+str(M)+"variables"
        filepath=os.path.join(path+directory)
        AverageList=[]
        AxisX=[]
        ratio=2
        for ratio in frange(2,9,0.2):
            sourceSizeList=[]
            AxisX.append(ratio)
            L = int(ratio*M)
            for i in range(0,1):
                RandomPositiveCNF(L, M, K)
                filename="cnf_"+str(L)+"_"+str(M)
                size=GetJavaOutput(filepath, filename)
                sourceSizeList.append(size)
                os.remove(os.path.join(filepath,filename))
            avg=FindAVG(sourceSizeList)  
            AverageList.append(avg)   
        trace = go.Scatter(
            x = AxisX,
            y = AverageList,
            name = str(M)+"variabls",
            line = dict(
                color = ("rgb("+str(random.randint(1,256))+","+str(random.randint(1,256))+","+str(random.randint(1,256))+")"),
                width = 4)
            )
        data.append(trace)   
    layout = dict(title = 'test average',
              xaxis = dict(title = 'Rules and variables ratio'),
              yaxis = dict(title = 'Average Source Size'),
              )
 
    fig = dict(data=data, layout=layout)
    py.plot(fig, filename='averagesourcesize')    
    
    
   
def testDPcalls():
    K=3
    M=100
    avgDP=[]
    avgModuMin=[]
    AxisX=[]
    for ratio in frange(2,10,0.2):
       # print("ratio " , ratio)
        dpcalls_DP=[]
        dpcalls_ModuMin=[]
        AxisX.append(ratio)
        L = int(ratio*M)
        for i in range(0,80): 
           # print("i ",i)
            RandomPositiveCNF(L, M, K)
            filename="cnf_"+str(L)+"_"+str(M)
            output=GetJavaOutput(path, filename).split(",")
           # print(output)
            dpcalls_DP.append(output[1])
            dpcalls_ModuMin.append(output[0])
            os.remove(os.path.join(path,filename))
        avgDP.append(FindAVG(dpcalls_DP)) 
        avgModuMin.append(FindAVG(dpcalls_ModuMin))
    trace1=go.Scatter(
        x=AxisX,
        y=avgDP,
        name="only DP",
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
           # dash='dash'
            )
        )
    trace2=go.Scatter(
        x=AxisX,
        y=avgModuMin,
        name="modumin using DP",
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
            dash='dash'
            )
        )
    data=[trace1,trace2]
    layout = dict(title = 'Run time '+str(M)+" variables",
            xaxis = dict(title = 'Rules and variables ratio'),
            yaxis = dict(title = 'Average run time in miliseconds'),
            )
    fig = dict(data=data, layout=layout)  
    py.plot(fig, filename='run time test') 
    
   
   
   
def testMemUsage():
    K=3
    M=100
    avgMemWASP=[]
    avgModuMinUsingWASP=[]
    AxisX=[]
    for ratio in frange(2,10,0.2):
       # print("ratio " , ratio)
        mem_WASP=[]
        mem_ModuMinUsingWASP=[]
        AxisX.append(ratio)
        L = int(ratio*M)
        for i in range(0,80): 
           # print("i ",i)
            RandomPositiveCNF(L, M, K)
            filename="cnf_"+str(L)+"_"+str(M)
            output=GetJavaOutput(path, filename).split(",")
           # print(output)
            mem_WASP.append(output[0])
            mem_ModuMinUsingWASP.append(output[1])
            os.remove(os.path.join(path,filename))
        avgMemWASP.append(FindAVG(mem_WASP)) 
        avgModuMinUsingWASP.append(FindAVG(mem_ModuMinUsingWASP))
    trace1=go.Scatter(
        x=AxisX,
        y=avgMemWASP,
        name="WASP",
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
           # dash='dash'
            )
        )
    trace2=go.Scatter(
        x=AxisX,
        y=avgModuMinUsingWASP,
        name="ModuMin using wasp",
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
            dash='dash'
            )
        )
    data=[trace1,trace2]
    layout = dict(title = 'Memory usage in MB '+str(M)+" variables",
            xaxis = dict(title = 'Rules and variables ratio'),
            yaxis = dict(title = 'Memory usage in MB'),
            )
    fig = dict(data=data, layout=layout)  
    py.plot(fig, filename='Memory usage test')  
   
   
   
   
   
   
def avgSource():
    K=3
    M=100
    avgSourceSize=[]
    medSourceSize=[]
    AxisX=[]
    for ratio in frange(2,10,0.2):
        sourceSize=[]
        AxisX.append(ratio)
        L = int(ratio*M)
        for i in range(0,3): 
           # print("i ",i)
            RandomPositiveCNF(L, M, K)
            filename="cnf_"+str(L)+"_"+str(M)
            output=GetJavaOutput(path, filename)
            sourceSize.append(output)
            os.remove(os.path.join(path,filename))
        avgSourceSize.append(FindAVG(sourceSize)) 
        medSourceSize.append(FindMedian(sourceSize))
    trace1=go.Scatter(
        x=AxisX,
        y=avgSourceSize,
        name="Average source size",
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
           # dash='dash'
            )
        )
    trace2=go.Scatter(
        x=AxisX,
        y=medSourceSize,
        name="median source size",
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
            dash='dash'
            )
        )
    data=[trace1,trace2]
    layout = dict(title = 'Average and median source size '+str(M)+" variables",
            xaxis = dict(title = 'Rules and variables ratio'),
            yaxis = dict(title = 'source size'),
            )
    fig = dict(data=data, layout=layout)  
    py.plot(fig, filename='Source size test')    
   
   

def avgSource2():
    K=3
    L=500
    avgSourceSize=[]
    medSourceSize=[]
    AxisX=[]
    for ratio in frange(2,10,0.2):
        sourceSize=[]
        AxisX.append(ratio)
        M = int(L/ratio)
        for i in range(0,300): 
           # print("i ",i)
            RandomPositiveCNF(L, M, K)
            filename="cnf_"+str(L)+"_"+str(M)
            output=GetJavaOutput(path, filename)
            sourceSize.append(output)
            os.remove(os.path.join(path,filename))
        avgSourceSize.append(FindAVG(sourceSize)) 
        medSourceSize.append(FindMedian(sourceSize))
    trace1=go.Scatter(
        x=AxisX,
        y=avgSourceSize,
        name="Average source size",
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
           # dash='dash'
            )
        )
   
    data=[trace1]
    layout = dict(title = 'Average source size '+str(L)+" rules",
            xaxis = dict(title = 'Rules and variables ratio'),
            yaxis = dict(title = 'source size'),
            )
    fig = dict(data=data, layout=layout)  
    py.plot(fig, filename='Source size test')    

avgSource2()       
#testMemUsage() 
    
    
    
    
    
    
def checkModuMin():
    K=3
    M=100
    for ratio in frange(2,10,0.2):
        L = int(ratio*M)
        myFile=RandomPositiveCNF(L, M, K)   
        filename="cnf_"+str(L)+"_"+str(M)
        output=GetJavaOutput(path, filename)
        print(output)
        
#checkModuMin()   
#RandomPositiveCNF(150, 50, 3)        
#memoryTest()
# RandomPositiveCNF(150, 50, 3)
# s=GetMemoryFromJava("/home/rachel/Desktop/Modular-Construction-of-Minimal-Models/testFiles/50variables", "cnf_150_50")
# print(s)





