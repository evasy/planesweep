import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;


public class OldMain {

    class PlaneSweepMaxDistance {

        public static double maxDistanceUsingPlaneSweep(double[] A, double[] B) {
            ArrayList<Point> listA = convertToCoordinates(A);
            ArrayList<Point> listB = convertToCoordinates(B);

            List<Double> allXCoordinates = new ArrayList<>();
            for (Point c : listA) {
                allXCoordinates.add(c.longitude);
            }
            for (Point c : listB) {
                allXCoordinates.add(c.longitude);
            }
            Collections.sort(allXCoordinates);

            TreeMap<Double, Double> activeYCoordinates = new TreeMap<>();

            double maxDistance = Double.MIN_VALUE;

            for (double x : allXCoordinates) {
                ArrayList<Point> candidates = new ArrayList<>();

                for (Point c : listA) {
                    if (c.longitude == x) {
                        candidates.add(c);
                    }
                }
                for (Point c : listB) {
                    if (c.longitude == x) {
                        candidates.add(c);
                    }
                }

                for (Point c : candidates) {
                    if (activeYCoordinates.isEmpty()) {
                        activeYCoordinates.put(c.latitude, c.latitude);
                        continue;
                    }

                    Double lower = activeYCoordinates.floorKey(c.latitude);
                    Double higher = activeYCoordinates.ceilingKey(c.latitude);

                    if (lower != null) {
                        maxDistance = Math.max(maxDistance, distanceLatLngSq(c.latitude, lower, c.longitude, x));
                    }
                    if (higher != null) {
                        maxDistance = Math.max(maxDistance, distanceLatLngSq(c.latitude, higher, c.longitude, x));
                    }

                    activeYCoordinates.put(c.latitude, c.latitude);
                }
            }

            return maxDistance;
        }


        public static double[] test1 = {
                47.6622087,-122.3143449,
                47.6631055,-122.314385,
                47.6630665,-122.3143859,
                47.6630731,-122.3143747,
                47.6631603,-122.3143494,
                47.6632041,-122.3143451,
                47.6632341,-122.3143563,
                47.6632875,-122.3143431,
                47.6633156,-122.3143373,
                47.6633398,-122.3143116,
                47.6633756,-122.3142802,
                47.6633981,-122.3142642,
                47.6634185,-122.3142397,
                47.6634346,-122.3142229,
                47.6634718,-122.3142137,
                47.6635117,-122.3142069,
                47.6635467,-122.3142005,
                47.6635984,-122.3141986,
                47.6636345,-122.3141968,
                47.6636804,-122.3141994,
                47.6637096,-122.314201,
                47.663741,-122.3142045,
                47.6637644,-122.3142115,
                47.6637818,-122.3142135,
                47.6638025,-122.3142072,
                47.6638254,-122.314215,
                47.6638498,-122.3142247,
                47.6638926,-122.3142346,
                47.6639383,-122.3142295,
                47.6639892,-122.3142421,
                47.6640245,-122.3142496,
                47.6640515,-122.3142577,
                47.6640924,-122.3142668,
                47.6641151,-122.3142703,
                47.6641417,-122.3142841,
                47.6641712,-122.314284,
                47.664199,-122.3142774,
                47.664218,-122.3142861,
                47.6642395,-122.3142925,
                47.6642555,-122.314292,
                47.6642771,-122.3142886,
                47.6642986,-122.3142911,
                47.6643357,-122.314291,
                47.6643629,-122.3142909,
                47.6643699,-122.3142855,
                47.6643955,-122.314282,
                47.6644127,-122.3142816,
                47.6644286,-122.3142861,
                47.6644562,-122.314296,
                47.6644845,-122.3143215,
                47.6645137,-122.3143289,
                47.6645391,-122.314325,
                47.664559,-122.3143166,
                47.6645813,-122.3143162,
                47.6646013,-122.314351,
                47.6646309,-122.3143731,
                47.6646662,-122.314389,
                47.6647139,-122.3144065,
        };

        static double[] test2 = {
                47.6631007,-122.3136975,
                47.663036,-122.3141844,
                47.6630508,-122.3142278,
                47.6630549,-122.3142677,
                47.6630668,-122.3142727,
                47.6631743,-122.3142841,
                47.6631886,-122.3142854,
                47.6632234,-122.3142878,
                47.6632902,-122.3142585,
                47.6633379,-122.3142359,
                47.6633793,-122.3142255,
                47.6634186,-122.314205,
                47.6634478,-122.3141935,
                47.6634974,-122.3141992,
                47.663535,-122.3141932,
                47.6635768,-122.3141733,
                47.6636115,-122.3141767,
                47.663646,-122.3141868,
                47.6636665,-122.3141908,
                47.6636861,-122.314192,
                47.6637115,-122.3141852,
                47.6637375,-122.3141943,
                47.6637631,-122.3142082,
                47.6637905,-122.3142257,
                47.6638135,-122.3142449,
                47.6638498,-122.31425,
                47.6639145,-122.3142476,
                47.6639553,-122.3142472,
                47.6639891,-122.3142467,
                47.6640188,-122.3142506,
                47.6640676,-122.3142548,
                47.6640989,-122.314272,
                47.6641204,-122.3142888,
                47.6641441,-122.3142988,
                47.6641735,-122.3143076,
                47.6642242,-122.3142848,
                47.6642533,-122.3142759,
                47.6642732,-122.3142869,
                47.664306,-122.3142707,
                47.6643343,-122.3142597,
                47.6643602,-122.3142536,
                47.6643838,-122.3142474,
                47.6644083,-122.3142439,
                47.6644179,-122.3142411,
                47.66444,-122.3142648,
                47.6644722,-122.3143226,
                47.6645202,-122.3143537,
                47.6645535,-122.3143654,
                47.664576,-122.3144062,
                47.6646063,-122.3144156,
                47.6646484,-122.3143825,
                47.664687,-122.3143784,
                47.6647219,-122.3143815,
                47.664782,-122.3144036,

        };

        static double[] test3 = {47.6638314,-122.3142409,
                47.6629648,-122.3144314,
                47.6629683,-122.3144235,
                47.663027,-122.314418,
                47.6630327,-122.3144142,
                47.6630362,-122.3144189,
                47.6630384,-122.3144461,
                47.663036,-122.3144831,
                47.6630394,-122.3145376,
                47.663036,-122.3145679,
                47.6630393,-122.3146458,
                47.663024,-122.3147064,
                47.6630059,-122.3147617,
                47.6629846,-122.3148092,
                47.6629587,-122.3148459,
                47.6629551,-122.3149072,
                47.662981,-122.3150038,
                47.6630386,-122.315139,
                47.6630747,-122.315253,
                47.6630686,-122.3153225,
                47.6630277,-122.3153442,
                47.6629747,-122.3153342,
                47.6629383,-122.315316,
                47.6629086,-122.31532,
                47.6628829,-122.3153273,
                47.6628528,-122.3153184,
                47.6628036,-122.315318,
                47.6627731,-122.3153136,
                47.6627405,-122.3153122,
                47.6627137,-122.3153181,
                47.6626944,-122.3153407,
                47.6626626,-122.3153527,
                47.6626389,-122.3153569,
                47.6625946,-122.3153514,
                47.6625584,-122.3153431,
                47.6625251,-122.3153396,
                47.6624831,-122.315336,
                47.6624274,-122.3153368,
                47.6623714,-122.315344,
                47.6623244,-122.3153391,
                47.6622878,-122.3153462,
                47.6622422,-122.3153612,
                47.6622095,-122.3153716,
                47.6621733,-122.3153833,
                47.6621358,-122.3153915,
                47.6621147,-122.3153857,
                47.6620895,-122.3153805,
                47.6620618,-122.3153854,
                47.6620361,-122.3153889,

        };

        static double[] test4 = {
                47.6630962,-122.314286,
                47.6630829,-122.3142387,
                47.6630865,-122.3143366,
                47.6630873,-122.3143681,
                47.6630887,-122.3144166,
                47.6630794,-122.3144805,
                47.6630696,-122.3145538,
                47.6630488,-122.3146449,
                47.6630423,-122.3147141,
                47.6630531,-122.3147816,
                47.6630554,-122.3148268,
                47.6630568,-122.3148783,
                47.6630616,-122.3149426,
                47.663058,-122.3150106,
                47.6630609,-122.3150801,
                47.6630645,-122.3151559,
                47.6630595,-122.315226,
                47.6630245,-122.3152908,
                47.6629875,-122.3152906,
                47.6629498,-122.3152756,
                47.6629112,-122.3152566,
                47.6628844,-122.3152668,
                47.6628521,-122.3152607,
                47.662814,-122.3152589,
                47.6627752,-122.315265,
                47.6627426,-122.3152665,
                47.6627053,-122.3152719,
                47.6626734,-122.3153152,
                47.6626413,-122.3153498,
                47.6626125,-122.3153669,
                47.6625897,-122.3153804,
                47.6625588,-122.3153937,
                47.6625022,-122.3153926,
                47.6624542,-122.3153913,
                47.662413,-122.3153859,
                47.662373,-122.3153873,
                47.6623323,-122.3153757,
                47.6623074,-122.315366,
                47.662266,-122.3153567,
                47.6622258,-122.3153524,
                47.6621911,-122.3153504,
                47.6621604,-122.3153517,
                47.6621414,-122.3153633,
                47.6621133,-122.3153734,
                47.6620856,-122.3153745,
                47.6620614,-122.3153735,
                47.6620257,-122.3153766,
        };


        static double[] test5 = {47.6620076,-122.3153861,
                47.662049,-122.3154005,
                47.6620768,-122.3154032,
                47.6620971,-122.3154053,
                47.6621225,-122.3154183,
                47.6621581,-122.315417,
                47.6621913,-122.3154161,
                47.6622404,-122.3154099,
                47.6623044,-122.315399,
                47.6623452,-122.3153938,
                47.6623859,-122.3153864,
                47.6624204,-122.3153782,
                47.6624426,-122.315368,
                47.6624672,-122.3153628,
                47.6624993,-122.31537,
                47.6625247,-122.3153774,
                47.6625553,-122.3153901,
                47.6625837,-122.3154025,
                47.6626202,-122.3154103,
                47.6626534,-122.315405,
                47.662686,-122.3154047,
                47.6627278,-122.315379,
                47.6627476,-122.3153403,
                47.6627748,-122.3152941,
                47.6627998,-122.3152867,
                47.6628326,-122.315283,
                47.6628472,-122.3152625,
                47.6628591,-122.3152408,
                47.6628901,-122.3152209,
                47.6629178,-122.3151986,
                47.662949,-122.3152218,
                47.6629786,-122.3152279,
                47.6630073,-122.3152276,
                47.6630445,-122.3152551,
                47.6630664,-122.3152475,
                47.6630928,-122.3152246,
                47.6631144,-122.3151807,
                47.663128,-122.3151463,
                47.6631275,-122.3150615,
                47.6631322,-122.3149994,
                47.6631062,-122.3148739,
                47.6631011,-122.3147945,
                47.6631018,-122.3147152,
                47.663102,-122.3145991,
                47.6631098,-122.3145167,
                47.6631165,-122.3144694,
                47.6631218,-122.314425,
                47.6631244,-122.3143744,
                47.6631099,-122.3143272,
        };
        static double[] test6 = {47.6620024,-122.3153849,
                47.6620363,-122.3153922,
                47.6620529,-122.315394,
                47.6620784,-122.3153991,
                47.6621029,-122.3154111,
                47.6621342,-122.3154212,
                47.6621729,-122.3154278,
                47.6622012,-122.3154388,
                47.6622657,-122.3154206,
                47.6623153,-122.3154066,
                47.6623445,-122.3154025,
                47.6623805,-122.3153985,
                47.6624176,-122.3153983,
                47.6624505,-122.315401,
                47.6624734,-122.315414,
                47.6624932,-122.3154244,
                47.6625168,-122.3154212,
                47.6625525,-122.3154125,
                47.6625976,-122.3154057,
                47.6626544,-122.3153971,
                47.6627151,-122.315392,
                47.6627538,-122.3153921,
                47.6627792,-122.3153817,
                47.6627985,-122.3153322,
                47.662825,-122.315319,
                47.6628425,-122.3153234,
                47.6628549,-122.3153194,
                47.6628529,-122.3153156,
                47.6628666,-122.3153137,
                47.6629049,-122.3153088,
                47.6629558,-122.3153066,
                47.6629864,-122.315334,
                47.663008,-122.3153482,
                47.6630438,-122.3153313,
                47.6630576,-122.3152968,
                47.6630575,-122.3152572,
                47.6630497,-122.3152008,
                47.6630384,-122.3151247,
                47.6630297,-122.3150587,
                47.6630215,-122.3149826,
                47.6630183,-122.3149079,
                47.6630194,-122.3148452,
                47.6630174,-122.3147884,
                47.6630279,-122.3147202,
                47.6630547,-122.3146061,
                47.6630738,-122.314527,
                47.6630786,-122.3144429,
                47.6630777,-122.3143843,
                47.6630895,-122.3143411,
        };


//    public static double distance(Coordinate p1, Coordinate p2) {
//
//        double lat1 = p1.latitude;
//        double lat2 = p2.latitude;
//        double lon1 = p1.longitude;
//        double lon2 = p2.longitude;
//        return Math.pow(((lat1-lat2)*111139),2) + Math.pow(((lon1 - lon2)*111139),2);
//    }

        public static double distanceLatLngSq(double lat1, double lat2, double lon1, double lon2) {
            return Math.pow(((lat1-lat2)*111139),2) + Math.pow(((lon1 - lon2)*111139),2);
        }

        public static ArrayList<Point> convertToCoordinates(double[] list) {
            ArrayList<Point> l = new ArrayList<>();
            for(int i = 0; i < list.length-1; i+=2) {
                Point c = new Point(list[i], list[i+1]);
                l.add(c);
            }
            return l;
        }

        public static ArrayList<LineSegment> convertToLineSegments(ArrayList<Point> list) {
            ArrayList<LineSegment> lineSegments = new ArrayList<>();

            for (int i = 0; i < list.size() -1; i += 2) {
                LineSegment segment = new LineSegment(list.get(i), list.get(i + 1));
                lineSegments.add(segment);
            }
            return lineSegments;
        }


        public static double maxDistanceUsingNearestNeighbor(double[] A, double[] B) {
            ArrayList<Point> listA = convertToCoordinates(A);
            ArrayList<Point> listB = convertToCoordinates(B);

            double maxdistance = Double.MIN_VALUE;

            for (Point point1 : listA) {
                double curr = Double.MAX_VALUE;
                for (Point point2 : listB) {
                    double distance = distanceLatLngSq(point1.getLatitude(), point2.getLatitude(),point1.getLatitude(), point2.getLongitude() );
                    if (distance < curr) {
                        curr = distance;
                    }
                }
                if (curr > maxdistance) {
                    maxdistance = curr;
                }
            }
            return Math.sqrt(maxdistance);
        }


        public static void main(String[] args) {


            System.out.println( maxDistanceUsingNearestNeighbor(test1, test2));
            System.out.println( maxDistanceUsingNearestNeighbor(test3, test4));
            System.out.println( maxDistanceUsingNearestNeighbor(test5, test6));



            //        System.out.println(test1.length);
//        System.out.println(test2.length);
//        System.out.println(test5.length);
//        System.out.println(test6.length);


//
//
//        ArrayList<Double> distances2 = new ArrayList<>();
//        for(int i = 0; i < test3.length-1; i+=2) {
//            distances2.add(distance(test3[i],test4[i],test3[i+1],test4[i+1]));
//        }
//
//        System.out.println(Collections.max(distances2)); // max difference
//
            ArrayList<Double> distances3 = new ArrayList<>();
            for(int i = 0; i < test5.length-1; i+=2) {
                distances3.add(Math.sqrt(distanceLatLngSq(test5[i],test6[i],test5[i+1],test6[i+1])));
            }

            System.out.println(Collections.max(distances3)); // max difference


        }


    }}


//    public static double distance(double lat1, double lat2, double lon1,
//                                  double lon2) {
//
//        final int R = 6371; // Radius of the earth in kilometers
//
//        double latDistance = Math.toRadians(lat2 - lat1);
//        double lonDistance = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
//                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
//                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double distance = R * c * 1000; // convert to meters
//
//        // distance = Math.pow(distance, 2);
//        // return Math.sqrt(distance);
//        return distance;
//    }

//    private static double deg2rad(double deg) {
//        return (deg * Math.PI / 180.0);
//    }
//
//    private static double rad2deg(double rad) {
//        return (rad * 180.0 / Math.PI);
//    }

//system.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "M") + " Miles\n");
//system.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "K") + " Kilometers\n");
//system.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "N") + " Nautical Miles\n");