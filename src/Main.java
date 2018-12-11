import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String args[]) {
        List<String> input = readInput();
        Map<String, Node> nodes = new HashMap<>();
        for (String str : input) {
            String[] splitStr = str.split(" ");
            Node parentNode = getNode(nodes, splitStr[7]);
            Node childNode = getNode(nodes, splitStr[1]);
            nodes.put(parentNode.getCode(), parentNode);
            nodes.put(childNode.getCode(), childNode);
            childNode.getParents().add(parentNode);
            parentNode.getChilder().add(childNode);
        }
        Node root = nodes.entrySet().stream()
                .filter(e -> e.getValue().getChilder().isEmpty())
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
        System.out.print(root.code);
        printTree(root, new HashSet<>(), new ArrayList<>());
    }

    private static void printTree(Node node, Set<Node> shownNodes, List<Node> waitingNodes) {
        shownNodes.add(node);
        waitingNodes.addAll(node.getParents().stream()
                .filter(parentNode -> !shownNodes.contains(parentNode) && shownNodes.containsAll(parentNode.childer))
                .collect(Collectors.toList()));
        Node nextNode = waitingNodes.stream().min(Comparator.comparing(Node::getCode)).orElse(null);
        if (nextNode != null) {
            System.out.print(nextNode.getCode());
            waitingNodes.remove(nextNode);
            printTree(nextNode, shownNodes, waitingNodes);
        }
    }

    public static Node getNode(Map<String, Node> nodes, String code) {
        Node node = nodes.get(code);
        if (node == null) {
            node = new Node(code);
        }
        return node;
    }

    public static class Node {
        private String code;
        private List<Node> parents = new ArrayList<>();
        private List<Node> childer = new ArrayList<>();

        public Node(String code) {
            this.setCode(code);
        }

        public String getCode() {
            return code;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "code='" + getCode() + '\'' +
                    ", parents=" + getParents() +
                    '}';
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<Node> getParents() {
            return parents;
        }

        public void setParents(List<Node> parents) {
            this.parents = parents;
        }

        public List<Node> getChilder() {
            return childer;
        }

        public void setChilder(List<Node> childer) {
            this.childer = childer;
        }
    }


    public static List<String> readInput() {
        List<String> ret = new ArrayList<>();
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader("input");
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                ret.add(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return ret;
        }
    }
}
