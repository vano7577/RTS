class SMO:
    currentTime = 0
    Q = []  # Task queue
    Qready = []
    Tw = [0.0 for _ in range(10000)]
    Tn = [0 for _ in range(10000)]
    faults = [0 for _ in range(10000)]
    currentTask = None

    def __init__(self, Q):
        self.Q = Q

    def GetEDTask(self, removeFromQ):
        timeBuf = 9999999
        taskwED = None
        for i in range(len(self.Qready)):
            newTime = self.Qready[i].GetDeadline()
            if timeBuf > newTime:
                timeBuf = newTime
                taskwED = self.Qready[i]
        if (removeFromQ):
            self.Qready.remove(taskwED)
        return taskwED

    def ToReadyQueue(self):
        for i in range(len(self.Q)):
            if self.Q[i].GetCreationTime() == self.currentTime:
                self.Qready.append(self.Q[i])

    def CheckForDeadlines(self):
        flt = 0
        flti = []
        for i in range(len(self.Qready)):
            if self.Qready[i].GetDeadline() < self.currentTime:
                flt += 1
                flti.append(i)
        if self.currentTime == 0:
            self.faults[self.currentTime] = flt
        else:
            self.faults[self.currentTime] = self.faults[self.currentTime - 1] + flt
        for i in range(len(flti)):
            del self.Qready[flti[i]]
            for j in range(i, len(flti)):
                flti[j] -= 1

    def Work(self,isRM, isEDF):
        self.currentTime = 0

        for self.currentTime in range(10000):
            if self.currentTime != 0:
                self.Tn[self.currentTime] = self.Tn[self.currentTime - 1]
            timewait = 0
            self.CheckForDeadlines()
            self.ToReadyQueue()
            if self.currentTask is not None and self.currentTask.GetExecutionTime() == 0:
                self.currentTask = None
            elif self.currentTask is not None and self.GetEDTask(False) is not None and isRM:
                if self.GetEDTask(False).GetExecutionTime() < self.currentTask.GetExecutionTime():
                    self.Qready.append(self.currentTask)
                    self.currentTask = self.GetEDTask(True)
            elif self.currentTask is not None and self.GetEDTask(False) is not None and isEDF:
                if self.GetEDTask(False).GetDeadline() < self.currentTask.GetDeadline():
                    self.Qready.append(self.currentTask)
                    self.currentTask = self.GetEDTask(True)
            elif self.currentTask is not None:
                self.currentTask.WorkedOn()
            if self.GetEDTask(False) is None and self.currentTask is None:
                self.Tn[self.currentTime] += 1
                continue
            elif self.currentTask is None:
                self.currentTask = self.GetEDTask(True)
            for task in self.Qready:
                task.Wait()
                #timewait += 1
            self.Tw[self.currentTime] += 1# timewait

    def GetWaitTimes(self):
        return self.Tw

    def GetFaults(self):
        return self.faults

    def GetProcessorFreeTime(self):
        return self.Tn