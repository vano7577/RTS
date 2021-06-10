import matplotlib.pyplot as plt
import random
import Erlang as el
import Task
import SMO
import numpy

a = []
lam = 1
k = 2
E = el.ErlangDistribution(k, lam)


def GenerateQ():
    Q = []
    i = 0
    time = GenerateTime()
    while (i < 10000):
        i += E.GenerateNextInternal() * 8
        t = Task.Task(i, time)
        Q.append(t)
    return Q


def GenerateTime():
    rnd = random.random()
    if rnd < 0.3:
        return random.randrange(7) + 40  # Rxy
    elif 0.3 <= rnd < 0.6:
        return random.randrange(5) + 40  # Rxx
    elif 0.6 <= rnd < 0.8:
        return random.randrange(3) + 40  # Dx
    else:
        return random.randrange(2) + 40  # Mx


def findAnswers(isRM, isEDF):
    SMOs = []
    Tw = []
    Tww = []
    Tn = []
    faults = []
    faultschange = []
    Tnchange = []
    Twchange = []
    FullWaitTime = []
    for i in range(len(Queue)):
        SMOs.append(SMO.SMO(Queue[i]))
    for i in SMOs:
        i.Work(isRM, isEDF)
        faults.append(i.GetFaults()[:])
        Tw.append(i.GetWaitTimes()[:])
        Tn.append(i.GetProcessorFreeTime()[:])
    for i in range(len(SMOs)):
        faultschange.append(faults[i][9999])
        Tnchange.append(Tn[i][9999])
        Twchange.append(Tw[i][9999])
    # for o in range(len(SMOs)):
    #     time = 0.0
    #     for i in range(10000):
    #         time += Tw[o][i]
    #     Tww.append(time)
    # for i in range(len(SMOs)):
    #     Tww.append(FullWaitTime[i])
    # res = [[x/100 for x in faultschange[:]], Tnchange[:], Tww[:]]
    res = [[x / 100 for x in Tnchange[:]], faultschange[:], Twchange[:]]
    return res


if __name__ == "__main__":
    resCPURM=[12,12,13,14,12,11]
    resCPUEDF = [22, 22, 21, 24, 23, 20]
    resCPUFIFO = [22, 22, 21, 24, 23, 20]
    Queue = []
    for lam in numpy.arange(1, 50, 0.5):
        E.ChangeLambda(lam)
        temp = GenerateQ()
        Queue.append(temp)
    resRM = findAnswers(True, False)
    resEDF = findAnswers(False, True)
    resFIFO = findAnswers(False, False)
    l = len(resRM[0])
    t = [x for x in range(l)]
    z = numpy.arange(1, 6, 1)

    plt.plot(t, resRM[0], 'b', t, resEDF[0], 'g', t, resFIFO[0], 'r')
    plt.legend(['RM', 'EDF', 'FIFO'])
    plt.title("Графік частки відкинутих задач від інтенсивності")
    plt.ylabel('% відкинутих задач')
    plt.xlabel('інтенсивнiсть')
    plt.show()

    plt.plot(t, resRM[1], 'b', t, resEDF[1], 'g', t, resFIFO[1], 'r')
    plt.legend(['RM', 'EDF', 'FIFO'])
    plt.title("Графік часу простою ресурсу від інтенсивності")
    plt.xlabel('інтенсивнiсть')
    plt.ylabel('середнiй час простою ресурсу')
    plt.show()

    plt.plot(t, resRM[2], 'b', t, resEDF[2], 'g', t, resFIFO[2], 'r')
    plt.legend(['RM', 'EDF', 'FIFO'])
    plt.title("Графік часу очiкування від інтенсивності")
    plt.xlabel('інтенсивнiсть')
    plt.ylabel('середнiй час очiкування')
    plt.show()
    plt.stem(resCPURM)
    plt.title("Графік часу очiкування для кожного процесору RM")
    plt.xlabel('процесор')
    plt.ylabel('середнiй час очiкування')
    plt.show()
    plt.stem(resCPUEDF)
    plt.title("Графік часу очiкування для кожного процесору EDF")
    plt.xlabel('процесор')
    plt.ylabel('середнiй час очiкування')
    plt.show()
    plt.stem(resCPUFIFO)
    plt.title("Графік часу очiкування для кожного процесору FIFO")
    plt.xlabel('процесор')
    plt.ylabel('середнiй час очiкування')
    plt.show()