package controller;

import dal.BookingDAO;
import model.Booking;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ManageBookingsController", urlPatterns = {"/manageBookings"})
public class ManageBookingsController extends HttpServlet {

    
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    BookingDAO dao = new BookingDAO();
    List<Booking> list = dao.getAllBookings();
    
    
    request.setAttribute("bookingList", list); 
    request.getRequestDispatcher("admin/manage_bookings.jsp").forward(request, response);
}
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        String action = request.getParameter("action");
        String bookingIdStr = request.getParameter("bookingId");
        
        if (action != null && bookingIdStr != null) {
            int bookingId = Integer.parseInt(bookingIdStr);
            BookingDAO dao = new BookingDAO();
            String nextStatus = "";
            
           
            switch (action) {
                case "approve":
                    nextStatus = "Approved"; 
                    break;
                case "reject":
                    nextStatus = "Rejected"; 
                    break;
                case "confirmDeposit":
                    nextStatus = "Confirmed"; 
                    break;
                case "deliver":
                    nextStatus = "PickedUp"; 
                    break;
            }

            
            if (!nextStatus.isEmpty()) {
                dao.updateBookingStatus(bookingId, nextStatus);
            }
        }
        
        // Xử lý xong thì tự động load lại trang danh sách
        response.sendRedirect("manageBookings");
    }
}