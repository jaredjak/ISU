//package newQuestions;
//
//import java.util.ArrayList;
//
//public class MediaLibrary {
//	//instance variables
//	private ArrayList<Multimedia> mediaList;
//	
//	public MediaLibrary() {
//		mediaList = new ArrayList<Multimedia>();
//	}
//	
//	void addMedia(Multimedia m) {
//		// needed if statement to check if m is not null and has duration
//		mediaList.add(m);
//	}
//
//	void playAll() {
//		for (int i = 0; i < mediaList.size(); i++) {
//			mediaList.get(i).play();
//		}
//	}
//	
//	double getTotalDuration() {
//		double total = 0;
//		
//		for (int i = 0; i < mediaList.size(); i++) {
//			total += mediaList.get(i).getDuration();
//		}
//		
//		return total;
//	}
//	
//	ArrayList<String> getAllMetaData() {
//		ArrayList<String> metaData = new ArrayList<>();
//		
//		for (MultiMedia media : mediaList) {
//			metaData.add(String.valueOf(media));
//		}
//		
//		return metaData;
//	}
//}
