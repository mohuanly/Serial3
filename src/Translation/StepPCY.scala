package Translation

import StackNode.QListNode
import XPath.{Path, Step}

import scala.collection.mutable.ListBuffer
/**
  * Created by Jing Ao on 2016/2/15.
  */
class StepPCY(id: Int, label: String) extends TTNode(id, label) {
  override def translate(path: Path): Int = translate(path.step)
  override def translate(step: Step): Int = {
    println(this.id + ": " +  this.label + " ")
    val cid: Int = id
    if (step.preds.hasq1) {
      if (step.preds.hasq2) {
        if (step.preds.isPC) q1 = new PredPCYY(cid + 1, step.preds.getTest)
        else q1 = new PredADYY(cid + 1, step.preds.getTest)
      } else {
        if (step.preds.isPC) q1 = new PredPCYN(cid + 1, step.preds.getTest, null)
        else q1 = new PredADYN(cid + 1, step.preds.getTest, null)
      }
    } else {
      if (step.preds.hasq2) {
        if (step.preds.isPC) q1 = new PredPCNY(cid + 1, step.preds.getTest)
        else q1 = new PredADNY(cid + 1, step.preds.getTest)
      } else {
        if (step.preds.isPC) q1 = new PredPCNN(cid + 1, step.preds.getTest, null)
        else q1 = new PredADNN(cid + 1, step.preds.getTest, null)
      }
    }
    q1.translate(step.preds)
  }
  override def doMatch(test: String, qforx1: ListBuffer[QListNode], qforx2: ListBuffer[QListNode], redList: ListBuffer[QListNode]) = {
    Map
    //    qforx1.append(q1)
    //    redList.append(this)
    //    qforx2.append(this)
    qforx1 += new QListNode(q1, null)
    redList += new QListNode(this, null)
    qforx2 += new QListNode(this, null)
  }
  override def doNotMatch(test: String, qforx1: ListBuffer[QListNode], qforx2: ListBuffer[QListNode], redList: ListBuffer[QListNode]) = {
    //    qforx2.append(this)
    qforx2 += new QListNode(this, null)
  }
  override def Map = MapAllChild(q1)
  override def Reduce = {
    val r = ReduceAllChild(q1)
    if (!rStack.top) {
      rStack.pop()
      rStack.push(r)
    }
    if (output) println("Current result of " + this + " is " + rStack.top)
  }
}
