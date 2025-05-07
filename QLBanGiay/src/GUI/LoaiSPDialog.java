package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import BUS.LoaiBUS;
import DTO.LoaiDTO;

public class LoaiSPDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private DefaultTableModel modelLoai;
    private JTable tblLoai;
    private JTextField txtMaLoai, txtTenLoai;
    private LoaiBUS loaiBUS;
    private JButton btnEditMode;
    private boolean isEditMode = false;

    public LoaiSPDialog(Window window) 
    {
        super(window, "Quản lý loại sản phẩm");
        loaiBUS = new LoaiBUS();
        initComponents();
        loaiBUS.docDSLoai(); // Đọc danh sách ngay khi mở GUI
        loadDataToTable();    // Hiển thị dữ liệu lên bảng
        setSize(500, 400);
        setLocationRelativeTo(window);
        setModal(true);
    }

    public void initComponents() {
        getContentPane().setLayout(null);

        JLabel lbTitle = new JLabel("QUẢN LÝ LOẠI SẢN PHẨM", SwingConstants.CENTER);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lbTitle.setBounds(4, 0, 486, 51);
        getContentPane().add(lbTitle);

        modelLoai = new DefaultTableModel(new String[]{"Mã loại", "Tên loại"}, 0);
        tblLoai = new JTable(modelLoai);
        tblLoai.setFont(new Font("Arial", Font.PLAIN, 13));
        tblLoai.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        JScrollPane scrollLoai = new JScrollPane(tblLoai);
        scrollLoai.setBounds(4, 50, 480, 200);
        getContentPane().add(scrollLoai);

        JLabel lbMaLoai = new JLabel("Mã loại:");
        lbMaLoai.setFont(new Font("Arial", Font.PLAIN, 14));
        lbMaLoai.setBounds(10, 260, 80, 25);
        getContentPane().add(lbMaLoai);

        txtMaLoai = new JTextField();
        txtMaLoai.setFont(new Font("Arial", Font.PLAIN, 13));
        txtMaLoai.setBounds(90, 260, 100, 25);
        txtMaLoai.setEditable(false);
        getContentPane().add(txtMaLoai);

        JLabel lbTenLoai = new JLabel("Tên loại:");
        lbTenLoai.setFont(new Font("Arial", Font.PLAIN, 14));
        lbTenLoai.setBounds(210, 260, 71, 25);
        getContentPane().add(lbTenLoai);

        txtTenLoai = new JTextField();
        txtTenLoai.setFont(new Font("Arial", Font.PLAIN, 13));
        txtTenLoai.setBounds(280, 260, 200, 25);
        txtTenLoai.setEditable(false);
        getContentPane().add(txtTenLoai);

        JButton btnThemLoai = new JButton("Thêm", new ImageIcon(getClass().getResource("/image/add20.png")));
        btnThemLoai.setForeground(Color.WHITE);
        btnThemLoai.setFont(new Font("Arial", Font.PLAIN, 13));
        btnThemLoai.setBackground(Color.decode("#4CAF50"));
        btnThemLoai.setBounds(56, 300, 120, 30);
        btnThemLoai.addActionListener(e -> themLoai());
        getContentPane().add(btnThemLoai);

        JButton btnSuaLoai = new JButton("Cập nhật", new ImageIcon(getClass().getResource("/image/edit20.png")));
        btnSuaLoai.setFont(new Font("Arial", Font.PLAIN, 13));
        btnSuaLoai.setForeground(Color.WHITE);
        btnSuaLoai.setBackground(Color.decode("#7986CB"));
        btnSuaLoai.setBounds(199, 300, 120, 30);
        btnSuaLoai.addActionListener(e -> suaLoai());
        getContentPane().add(btnSuaLoai);

        JButton btnXoaLoai = new JButton("Xóa", new ImageIcon(getClass().getResource("/image/remove20.png")));
        btnXoaLoai.setFont(new Font("Arial", Font.PLAIN, 13));
        btnXoaLoai.setForeground(Color.WHITE);
        btnXoaLoai.setBackground(Color.decode("#FF7043"));
        btnXoaLoai.setBounds(338, 300, 120, 30);
        btnXoaLoai.addActionListener(e -> xoaLoai());
        getContentPane().add(btnXoaLoai);

        btnEditMode = new JButton("");
        btnEditMode.setIcon(new ImageIcon(LoaiSPDialog.class.getResource("/image/editIcon.png")));
        btnEditMode.setBounds(362, 8, 43, 32);
        btnEditMode.setFocusPainted(false);
        btnEditMode.setBorderPainted(false);
        btnEditMode.setForeground(Color.WHITE);
        btnEditMode.setBackground(Color.decode("#B388FF"));
        btnEditMode.addActionListener(e -> toggleEditMode());
        getContentPane().add(btnEditMode);
        
        JButton btnRemove = new JButton("");
        btnRemove.setIcon(new ImageIcon(LoaiSPDialog.class.getResource("/image/clear20.png")));
        btnRemove.setFocusPainted(false);
        btnRemove.setBorderPainted(false);
        btnRemove.setBackground(Color.decode("#D4E157"));
        btnRemove.setBounds(401, 8, 43, 32);
        getContentPane().add(btnRemove);
        btnRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clearForm();
			}
		});
        tblLoai.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
//				clearForm();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				tblLoaiMouseClicked();
			}
		});
    }
    public void clearForm()
    {
    	txtMaLoai.setText("");
    	txtTenLoai.setText("");
    }
    
    public void tblLoaiMouseClicked()
    {
    	int i = tblLoai.getSelectedRow();
    	if(i >= 0)
    	{
    		txtMaLoai.setText(tblLoai.getValueAt(i, 0).toString());
    		txtTenLoai.setText(tblLoai.getValueAt(i, 1).toString());
    	}
    }
    
    public void toggleEditMode() 
    {
        isEditMode = !isEditMode;
        txtTenLoai.setEditable(isEditMode);
    }

    public void loadDataToTable() 
    {
        modelLoai.setRowCount(0);
        if (loaiBUS.getDsloai() != null) 
        {
            for (LoaiDTO loai : loaiBUS.getDsloai()) 
                modelLoai.addRow(new Object[]{loai.getMaLoaiSP(), loai.getTenLoaiSP()});
        }
    }

    public void themLoai() 
    {
        String tenLoai = txtTenLoai.getText().trim();
        int maLoai = loaiBUS.getNextID();
        LoaiDTO loai = new LoaiDTO(maLoai, tenLoai); 
        if (loaiBUS.add(loai)) 
        {
            txtMaLoai.setText("");
            txtTenLoai.setText("");
            JOptionPane.showMessageDialog(this, "Thêm loại SP thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            Vector row = new Vector();
            row.add(maLoai);
            row.add(tenLoai);
            modelLoai.addRow(row);
            tblLoai.setModel(modelLoai);
        } 
        else 
            JOptionPane.showMessageDialog(this, "Thêm thất bại! Tên loại không hợp lệ hoặc đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        
    }

    public void suaLoai() 
    {
    	if(isEditMode == false)
    	{
    		JOptionPane.showMessageDialog(this, "Vui lòng bật chế độ sửa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
    		return;
    	}
    	int i = tblLoai.getSelectedRow();
    	if(i >= 0)
    	{
    		LoaiDTO loai = new LoaiDTO();
    		int maLoai = Integer.parseInt(txtMaLoai.getText());
    		String tenLoai = txtTenLoai.getText();
    		loai.setMaLoaiSP(maLoai); loai.setTenLoaiSP(tenLoai);
    		
    		LoaiBUS loaiBUS = new LoaiBUS();
    		if(loaiBUS.update(loai))
    		{
    			Vector row = new Vector();
        		row.add(maLoai);
        		row.add(tenLoai);
        		modelLoai.setValueAt(maLoai, i, 0);
        		modelLoai.setValueAt(tenLoai, i, 1);
        		tblLoai.setModel(modelLoai);
        		JOptionPane.showMessageDialog(this, "Cập nhật thành công", 
        										"Thành công", JOptionPane.INFORMATION_MESSAGE);
    		}
    		else
    			JOptionPane.showMessageDialog(this, "Cập nhật thất bại !", 
    											"Thất bại", JOptionPane.ERROR_MESSAGE);
    	}
    	else
    		JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng dữ liệu để cập nhật",
    										"Lỗi", JOptionPane.ERROR_MESSAGE);
    		
    }

    public void xoaLoai() 
    {
        int i = tblLoai.getSelectedRow();
        if(i >= 0)
        {
        	int maLoai = Integer.parseInt(txtMaLoai.getText());
        	int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa sản phẩm có mã" +maLoai+"không ?",
        												"Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        	if(confirm == JOptionPane.YES_OPTION)
        	{	
        		LoaiBUS loaiBUS = new LoaiBUS();
        		if(loaiBUS.delete(maLoai))
            	{
        			clearForm();
        			modelLoai.removeRow(i);
        			tblLoai.setModel(modelLoai);
        			JOptionPane.showMessageDialog(this, "Xóa thành công !", 
        											"Thành công", JOptionPane.INFORMATION_MESSAGE);
            	}
        		else
        			JOptionPane.showMessageDialog(this, "Xóa thất bại !",
        											"Thất bại", JOptionPane.ERROR_MESSAGE);
        	}
        else
        	JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất 1 dòng dữ liệu để xóa",
        									"Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public DefaultTableModel getModelLoai() {
        return modelLoai;
    }
}