from Board import Board
from InputParser import InputParser
from AI import AI
import sys
import random

WHITE = True
BLACK = False


def askForPlayerSide():
    playerChoiceInput = input(
        "What side would you like to play as [wB]? ").lower()
    if 'w' in playerChoiceInput:
        print("You will play as white")
        return WHITE
    else:
        print("You will play as black")
        return BLACK


def askForDepthOfAI():
    depthInput = 2
    try:
        depthInput = int(input("How deep should the AI look for moves?\n"
                               "Warning : values above 3 will be very slow."
                               " [n]? "))
    except:
        print("Invalid input, defaulting to 2")
    return depthInput


def printCommandOptions():
    undoOption = 'u : undo last move'
    printLegalMovesOption = 'l : show all legal moves'
    randomMoveOption = 'r : make a random move'
    quitOption = 'quit : resign'
    moveOption = 'a3, Nc3, Qxa2, etc : make the move'
    options = [undoOption, printLegalMovesOption, randomMoveOption,
               quitOption, moveOption, '', ]
    print('\n'.join(options))


def printAllLegalMoves(board, parser):
    for move in parser.getLegalMovesWithNotation(board.currentSide, short=True):
        print(move.notation)


def getRandomMove(board, parser):
    legalMoves = board.getAllMovesLegal(board.currentSide)
    randomMove = random.choice(legalMoves)
    randomMove.notation = parser.notationForMove(randomMove)
    return randomMove


def makeMove(move, board):
    print("Making move : " + move.notation)
    board.makeMove(move)


def printPointAdvantage(board):
    print("Currently, the point difference is : " +
          str(board.getPointAdvantageOfSide(board.currentSide)))


def undoLastTwoMoves(board):
    if len(board.history) >= 2:
        board.undoLastMove()
        board.undoLastMove()


def startGame(board, playerSide, ai):
    parser = InputParser(board, playerSide)
    while True:
        print()
        print(board)
        print()
        if board.isCheckmate():
            if board.currentSide == playerSide:
                print("Checkmate, you lost")
            else:
                print("Checkmate! You won!")
            return

        if board.isStalemate():
            if board.currentSide == playerSide:
                print("Stalemate")
            else:
                print("Stalemate")
            return

        if board.currentSide == playerSide:
            move = playerMove(playerSide, board, 0, -99999, 99999)
            makeMove(move, board)

        else:
            move = ai.getBestMove()
            move.notation = parser.notationForMove(move)
            makeMove(move, board)

def playerMove(self, board, depth, alpha, beta):
    #if board.currentSide == playerSide:
    if depth == aiDepth:
        return #evaluate

    legalMoves = board.getAllMovesLegal(True)

    for move in legalMoves:
        score = aiMove(board.movePieceToPosition(move.piece, move), board, depth - 1, alpha, beta)

        board.undoLastMove()

    if score >= beta:
        return beta
    if score > alpha:
        alpha = score

    return alpha


def aiMove(self, board, depth, alpha, beta):

    print("AI thinking...")

    if depth == aiDepth:
        return #evaluate

    legalMoves = board.getAllMovesLegal(False)
    for move in legalMoves:
        score = playerMove(board.movePieceToPosition(move), board, depth - 1, alpha, beta)
        board.undoLastMove()

        if score <= alpha:
            return alpha

        if score < beta:
            beta = score
            
    return beta

            

board = Board()
playerSide = True
print()
aiDepth = 1
opponentAI = AI(board, not playerSide, aiDepth)

try:
    startGame(board, playerSide, opponentAI)
except KeyboardInterrupt:
    sys.exit()
