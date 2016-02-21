package Message

import StackNode.{QListNode, QList}
import Translation.{RStackNode, TTNode}
import akka.actor.ActorRef

import scala.collection.mutable

/**
  * Created by aojing on 2016/2/16.
  */
class Message(ttNodeID: Int, waitListID: Int, waitListNodeID: Int, qa: Int, qb: Int, qc: Int) {
  def send(dest: String) = {}
  def unpackMessage(ttNodeIndex: mutable.Map[Int, TTNode], qList: QList, sender: ActorRef) = {
    if (qa > 0) {
      val query1 = ttNodeIndex(qa)
      // TODO:push false然后还要记录sender，sender和false打包成栈结点？
      query1.rStack.push(new RStackNode(sender, ttNodeID, waitListID, waitListNodeID, false))
      qList.getqlist += new QListNode(query1, null)
    }
    if (qb > 0) {
      val query2 = ttNodeIndex(qb)
      // TODO:push false然后还要记录sender，sender和false打包成栈结点？
      query2.rStack.push(new RStackNode(sender, ttNodeID, waitListID, waitListNodeID, false))
      qList.getqlist += new QListNode(query2, null)
    }
    if (qc > 0) {
      val query3 = ttNodeIndex(qc)
      // TODO:push false然后还要记录sender，sender和false打包成栈结点？
      query3.rStack.push(new RStackNode(sender, ttNodeID, waitListID, waitListNodeID, false))
      qList.getqlist += new QListNode(query3, null)
    }
  }
}
