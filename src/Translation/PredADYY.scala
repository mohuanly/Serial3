package Translation

import StackNode.QListNode
import XPath.Pred

import scala.collection.mutable.ListBuffer
/**
  * Created by Jing Ao on 2016/2/15.
  */
class PredADYY(id: Int, label: String) extends TTNode(id, label) {
  override def translate(preds: Pred): Int = {
    println(this.id + ": " +  this.label + " ")
    var cid: Int = id
    if (preds.step.preds.hasq1) {
      if (preds.step.preds.hasq2) {
        if (preds.step.preds.isPC) q1 = new PredPCYY(cid + 1, preds.step.preds.getTest)
        else q1 = new PredADYY(cid + 1, preds.step.preds.getTest)
      } else {
        if (preds.step.preds.isPC) q1 = new PredPCYN(cid + 1, preds.step.preds.getTest, null)
        else q1 = new PredADYN(cid + 1, preds.step.preds.getTest, null)
      }
    } else {
      if (preds.step.preds.hasq2) {
        if (preds.step.preds.isPC) q1 = new PredPCNY(cid + 1, preds.step.preds.getTest)
        else q1 = new PredADNY(cid + 1, preds.step.preds.getTest)
      } else {
        if (preds.step.preds.isPC) q1 = new PredPCNN(cid + 1, preds.step.preds.getTest, null)
        else q1 = new PredADNN(cid + 1, preds.step.preds.getTest, null)
      }
    }
    cid = q1.translate(preds.step.preds)

    if (preds.preds.hasq1) {
      if (preds.preds.hasq2) {
        if (preds.preds.isPC) q2 = new PredPCYY(cid + 1, preds.preds.getTest)
        else q2 = new PredADYY(cid + 1, preds.preds.getTest)
      } else {
        if (preds.preds.isPC) q2 = new PredPCYN(cid + 1, preds.preds.getTest, null)
        else q2 = new PredADYN(cid + 1, preds.preds.getTest, null)
      }
    } else {
      if (preds.preds.hasq2) {
        if (preds.preds.isPC) q2 = new PredPCNY(cid + 1, preds.preds.getTest)
        else q2 = new PredADNY(cid + 1, preds.preds.getTest)
      } else {
        if (preds.preds.isPC) q2 = new PredPCNN(cid + 1, preds.preds.getTest, null)
        else q2 = new PredADNN(cid + 1, preds.preds.getTest, null)
      }
    }
    cid = q2.translate(preds.preds)
    q3 = new PredADYN(cid + 1, this.label, this)
    q3.translate(new Pred(preds.step, null))
  }
  override def getResult = {
    val r = rStack.pop()
    if (r && rStack.nonEmpty) {
      rStack.pop()
      rStack.push(true)
    }
    r
  }
  override def doMatch(test: String, qforx1: ListBuffer[QListNode], qforx2: ListBuffer[QListNode], redList: ListBuffer[QListNode]) = {
    //if (!rStack.top) {
    Map
    //      qforx1.append(q1)
    //      qforx1.append(q3)
    //      redList.append(this) //  只在match且压入q'''(x2)时做Reduce
    //      qforx2.append(q3)
    qforx1 += new QListNode(q1, null)
    qforx1 += new QListNode(q3, null)
    redList += new QListNode(this, null) //  只在match且压入q'''(x2)时做Reduce
    qforx2 += new QListNode(q3, null)
    //}
    q2.doWork(test, qforx1, qforx2, redList)
  }
  override def doNotMatch(test: String, qforx1: ListBuffer[QListNode], qforx2: ListBuffer[QListNode], redList: ListBuffer[QListNode]) = {
    //if (!rStack.top) {
    //      qforx1.append(q3)
    //      qforx2.append(q3)
    qforx1 += new QListNode(q3, null)
    qforx2 += new QListNode(q3, null)
    //}
    q2.doWork(test, qforx1, qforx2, redList)
  }
  override def Map = MapAllChild(q1)
  override def Reduce = {
    val r = ReduceAllChild(q1)
    if (!rStack.top) {
      rStack.pop()
      rStack.push(r)
    }
  }
}
