import { consoleLog } from "./console";
import { editDistanceRecursive, editDistanceDP } from "./minimumCost";
import {
  numOfPathsRecursive,
  numOfPathsDP,
  numOfPathsDP2,
} from "./rectangleTotalPath";
import { isInterleavingRecursive, isInterleavingDP } from "./interleaving";
import {
  isSubsetSumRecursive,
  isSubsetSumDP,
  isSubsetSumDP2,
} from "./subSetSum";

consoleLog("sunday", "saturday", editDistanceRecursive("sunday", "saturday"));
consoleLog("sunday", "saturday", editDistanceDP("sunday", "saturday"));

consoleLog("numOfPathsRecursive", numOfPathsRecursive(3, 4));
consoleLog("numOfPathsDP", numOfPathsDP(3, 4));
consoleLog("numOfPathsDP2", numOfPathsDP2(3, 4));

consoleLog(isInterleavingRecursive("bcc", "bbca", "bbcbcac"));
consoleLog(isInterleavingDP("bcc", "bbca", "bbcbcac"));

consoleLog(isSubsetSumRecursive([1, 2, 3, 4, 5], 5, 6));
consoleLog(isSubsetSumDP([1, 2, 3, 4, 5], 5, 6));
consoleLog(isSubsetSumDP2([1, 2, 3, 4, 5], 6));
