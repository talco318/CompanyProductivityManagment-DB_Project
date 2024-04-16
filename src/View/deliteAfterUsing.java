//
//		public Vector<Role> getRolListOfDepartment(String departmentName) {
//			try {
//				Vector<Role> roles = new Vector<Role>();
//
//				Statement stmt = conn.createStatement();
//				String query = String.format("SELECT * FROM dep_roleList_tbl WHERE name_department = '%s';", departmentName);
//				
//				ResultSet rs = stmt.executeQuery(query);
//			
//				while (rs.next()) {
//					
//				
//					String name_department=rs.getString(1);
//					String name_role=rs.getString(2);
//			
//					int start_hour = rs.getInt(3);
//					Role r=new Role(name_role, start_hour);
//					
//					int end_hour=rs.getInt(4);
//					r.setEndHour(end_hour);
//					String isSynchronize=rs.getString(5);
//					String workChange = rs.getString(6);
//					
//					if(isSynchronize.equals("false") )
//					{
//						r.setSynchronized(false);
//					}
//					
//					if(isSynchronize.equals("true"))
//					{
//						r.setSynchronized(true);
//					}
//					if(workChange.equals("false") )
//					{
//						r.setWorkChange(false);
//					}
//					
//					if(workChange.equals("true"))
//					{
//						r.setWorkChange(true);
//					}
//					
//					
//					roles.add(r);
//				}
//				stmt.close();
//				rs.close();
//				
//				
//		return roles;
//			} catch (Exception e) {
//				
//			}
//			
//		return null;
//		}
//		